package controller;

import controller.product.AllProductsController;
import database.Database;
import model.ecxeption.Exception;
import model.others.Filter;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final int port = 12222;
    private final ServerSocket socket;


    public Server() throws IOException {
        socket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        new Database().startDatabaseLoading();
        new Server().run();
    }

    private void run() {
        while (true) {
            new Thread(() -> {
                try {
                    handleClient(socket.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void handleClient(Socket socket) throws IOException {
        ArrayList<Filter> filters = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart();
        String sort = null;
        String sortDirection = null;
        ClientMessage request;
        ServerMessage answer;
        while (socket.isConnected()) {
            synchronized (Controller.getGson()) {
                request = Controller.getGson().fromJson(getMessage(socket), ClientMessage.class);
                Controller.setLoggedUser(request.getAuthToken());
                if (Controller.getLoggedUser() == null) {
                    updateFilters(filters, sort, sortDirection);
                    ShoppingCart.setLocalShoppingCart(shoppingCart);
                }
                answer = process(request);
                if (Controller.getLoggedUser() == null) {
                    sort = AllProductsController.getInstance().getSortField();
                    sortDirection = AllProductsController.getInstance().getSortDirection();
                    shoppingCart = ShoppingCart.getLocalShoppingCart();
                }
            }
            sendToSocket(socket, Controller.getGson().toJson(answer));
        }
        socket.close();
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

    private String getMessage(Socket socket) {
        StringBuilder message = new StringBuilder();
        while (socket.isConnected()) {
            String readText = readFromSocket(socket);
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

    private String readFromSocket(Socket socket) {
        try {
            return new DataInputStream(socket.getInputStream()).readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendToSocket(Socket socket, String message) {
        try {
            DataOutputStream socketOutputStream = new DataOutputStream(socket.getOutputStream());
            while (message.length() > 1000) {
                socketOutputStream.writeUTF("wait" + message.substring(0, 1000));
                socketOutputStream.flush();
                message = message.substring(1000);
            }
            socketOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
