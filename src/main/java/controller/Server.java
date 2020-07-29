package controller;

import com.google.gson.JsonSyntaxException;
import controller.product.AllProductsController;
import database.Database;
import model.ecxeption.Exception;
import model.others.Filter;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class Server {
    private static Server server;
    private final int port = 11121;
    private final ServerSocket socket;
    private final ArrayList<ClientThread> threads;
    private final ArrayList<SendMap> sendingQueue;

    public Server() throws IOException {
        socket = new ServerSocket(port);
        threads = new ArrayList<>();
        sendingQueue = new ArrayList<>();
        server = this;
    }

    public static void main(String[] args) throws IOException {
        new Database().startDatabaseLoading();
        System.out.println("Database updated");
        new Server().run();
    }

    public static Server getServer() {
        return server;
    }

    private void sendingThread() {
        while (true) {
            try {
                synchronized (sendingQueue) {
                    if (sendingQueue.size() != 0) {
                        for (ClientThread thread : threads) {
                            if (thread.getUser() == sendingQueue.get(0).user) {
                                thread.sendAnswer(sendingQueue.remove(0).serverMessage);
                                break;
                            }
                        }
                    }
                }
            } catch (ConcurrentModificationException ignored) {
            }
        }
    }

    public void removeThread(ClientThread thread) {
        synchronized (threads) {
            threads.remove(thread);
        }
    }

    private void run() {
        new Thread(this::sendingThread).start();
        while (true) {
            try {
                ClientThread thread = new ClientThread(socket.accept());
                threads.add(thread);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMessage(User user, ServerMessage serverMessage) {
        sendingQueue.add(new SendMap(user, serverMessage));
    }

    private static class SendMap {
        private final User user;
        private final ServerMessage serverMessage;

        public SendMap(User user, ServerMessage serverMessage) {
            this.user = user;
            this.serverMessage = serverMessage;
        }

        public User getUser() {
            return user;
        }

        public ServerMessage getServerMessage() {
            return serverMessage;
        }
    }
}


class ClientThread extends Thread {
    private final Socket socket;
    private final ArrayList<Filter> filters = new ArrayList<>();
    private final ArrayList<String> sendingQueue;
    private String token;
    private User user;
    private ShoppingCart shoppingCart = new ShoppingCart();
    private String sort = null;
    private String sortDirection = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
        token = "";
        sendingQueue = new ArrayList<>();
    }

    @Override
    public void run() {
        ClientMessage request;
        new Thread(this::sendQueue).start();
        while (socket.isConnected()) {
            synchronized (this) {
                request = getRequest();
                if (request == null) break;
                sendToSocket(processAnswer(request));
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendQueue() {
        while (true) {
            synchronized (sendingQueue) {
                if (sendingQueue.size() != 0) sendToSocket(sendingQueue.remove(0));
            }
        }
    }

    private String processAnswer(ClientMessage request) {
        ServerMessage answer;
        synchronized (Controller.getGson()) {
            if (!token.equals(request.getAuthToken())) {
                token = request.getAuthToken();
                user = Controller.getLoggedUser(token);
            }
            Controller.setLoggedUser(user);
            if (user == null) {
                updateFilters(filters, sort, sortDirection);
                ShoppingCart.setLocalShoppingCart(shoppingCart);
            }
            answer = process(request);
            answer.setId(request.getId());
            updateLocalVariables();
            return Controller.getGson().toJson(answer);
        }
    }

    public User getUser() {
        return user;
    }

    private ClientMessage getRequest() {
        String requestString = getMessage();
        ClientMessage request;
        synchronized (Controller.getGson()) {
            try {
                request = Controller.getGson().fromJson(requestString, ClientMessage.class);
                if (request == null) return null;
            } catch (JsonSyntaxException e) {
                Server.getServer().removeThread(this);
                return null;
            }
        }
        if (request.getHashMap() == null) request.setHashMap(new HashMap<>());
        if (request.getAuthToken() == null) request.setAuthToken("");
        return request;
    }

    private void updateLocalVariables() {
        if (Controller.getLoggedUser() == null) {
            sort = AllProductsController.getInstance().getSortField();
            sortDirection = AllProductsController.getInstance().getSortDirection();
            shoppingCart = ShoppingCart.getLocalShoppingCart();
        }
    }

    private ServerMessage process(ClientMessage request) {
        ServerMessage answer;
        try {
            answer = Controller.process(request);
        } catch (Exception e) {
            answer = new ServerMessage("Error", e.getMessage());
        }
        return answer;
    }

    private void updateFilters(ArrayList<Filter> filters, String sort, String sortDirection) {
        AllProductsController.getInstance().setFilters(filters);
        AllProductsController.getInstance().setSort(sort, sortDirection);
    }

    private String getMessage() {
        StringBuilder message = new StringBuilder();
        while (socket.isConnected()) {
            String readText = readFromSocket();
            assert readText != null;
            if (readText.startsWith("wait")) {
                message.append(readText.substring(4));
                continue;
            }
            message.append(readText);
            break;
        }
        return message.toString();
    }

    private String readFromSocket() {
        try {
            return new DataInputStream(socket.getInputStream()).readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendToSocket(String message) {
        try {
            DataOutputStream socketOutputStream = new DataOutputStream(socket.getOutputStream());
            while (message.length() > 1000) {
                socketOutputStream.writeUTF("wait" + message.substring(0, 1000));
                socketOutputStream.flush();
                message = message.substring(1000);
            }
            socketOutputStream.writeUTF(message);
            socketOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAnswer(ServerMessage message) {
        String string;
        synchronized (Controller.getGson()) {
            string = Controller.getGson().toJson(message);
        }
        synchronized (sendingQueue) {
            sendingQueue.add(string);
        }
    }
}
