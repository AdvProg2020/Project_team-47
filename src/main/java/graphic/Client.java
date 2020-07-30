package graphic;

import com.google.gson.Gson;
import controller.PortsAndIps;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    private static final Client client;
    private static int id = 0;

    static {
        client = new Client();
    }

    private final ArrayList<ServerMessage> answers;
    private final Gson gson;
    private final ArrayList<ClientMessage> clientMessages;
    private Socket socket;

    private Client() {
        gson = new Gson();
        answers = new ArrayList<>();
        clientMessages = new ArrayList<>();
        try {
            socket = new Socket(PortsAndIps.SERVER_IP, PortsAndIps.SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(11);
        }
        new Thread(this::gettingAnswer).start();
        new Thread(this::sendingQueue).start();
        new Thread(this::serverInterrupts).start();
    }

    private void serverInterrupts() {
        while (true) {
            synchronized (answers) {
                for (ServerMessage answer : answers) {
                    if ("-1".equals(answer.getId())) {
                        try {
                            serverMessage(answer);
                        } catch (IOException ignored) {
                        }
                        break;
                    }
                }
            }
        }
    }

    private void sendingQueue() {
        while (true) {
            synchronized (clientMessages) {
                for (ClientMessage clientMessage : clientMessages) {
                    try {
                        send(clientMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ServerMessage sendRequest(ClientMessage request) {
        try {
            request.setId(String.valueOf(id++));
            return client.send(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeSocket() throws IOException {
        client.socket.close();
    }

    private void serverMessage(ServerMessage answer) throws IOException {
        answers.remove(answer);
        switch (answer.getType()) {
            case "give me port" -> openPort(answer);
            case "download" -> download(answer);
        }
    }

    private void download(ServerMessage answer) throws IOException {
        new File("src/main/resources/download/").mkdirs();
        int port = Integer.parseInt(answer.getFirstString());
        Socket socket = new Socket(answer.getSecondString(), port);
        String path = "src/main/resources/download/" + getAnswer(socket);
        File file = new File(path);
        System.out.println(file.createNewFile());
        OutputStream outputStream = new FileOutputStream(file);
        byte[] fileBytes = gson.fromJson(getAnswer(socket), byte[].class);
        System.out.println("done");
        outputStream.write(fileBytes);
        try {
            outputStream.close();
        } catch (IOException ignored) {
            System.out.println("debug");
        }
        socket.close();
    }

    private void sendFile(Socket p2pCustomer, File file) throws IOException {
        sendRequest(p2pCustomer, file.getName());
        sendRequest(p2pCustomer, gson.toJson(new FileInputStream(file).readAllBytes()));
    }

    private void openPort(ServerMessage answer) throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("customer", answer.getFirstString());
        ClientMessage clientMessage = new ClientMessage("port opened");
        clientMessage.setFirstInt(serverSocket.getLocalPort());
        clientMessage.setHashMap(reqInfo);
        clientMessage.setId(String.valueOf(id++));
        new Thread(() -> {
            try {
                addMessage(clientMessage);
                sendFile(serverSocket.accept(), new File(answer.getSecondString()));
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addMessage(ClientMessage message) {
        synchronized (clientMessages) {
            clientMessages.add(message);
        }
    }

    private synchronized ServerMessage send(ClientMessage request) throws IOException {
        request.setAuthToken(MainFX.getInstance().getToken());
        if (request.getType().equals("logout")) MainFX.getInstance().setToken("");
        String message = gson.toJson(request);
        if (socket == null || !socket.isConnected()) socket = new Socket(PortsAndIps.SERVER_IP, PortsAndIps.SERVER_PORT);
        sendRequest(this.socket, message);
        return answer(request.getId());
    }

    private ServerMessage answer(String id) {
        while (true) {
            synchronized (answers) {
                for (ServerMessage answer : answers) {
                    if (id.equals(answer.getId())) {
                        answers.remove(answer);
                        return answer;
                    }
                }
            }
        }
    }

    private void sendRequest(Socket socket, String message) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        while (message.length() > 1000) {
            dataOutputStream.writeUTF("wait" + message.substring(0, 1000));
            message = message.substring(1000);
        }
        dataOutputStream.writeUTF(message);
    }

    private void gettingAnswer() {
        while (true) {
            try {
                String answer = getAnswer(this.socket);
                synchronized (answers) {
                    answers.add(gson.fromJson(answer, ServerMessage.class));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getAnswer(Socket socket) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        StringBuilder message = new StringBuilder();
        while (true) {
            String readText = dataInputStream.readUTF();
            if (readText.startsWith("wait")) {
                message.append(readText.substring(4));
                continue;
            }
            message.append(readText);
            break;
        }
        return message.toString();
    }
}
