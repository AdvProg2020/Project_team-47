package graphic;

import com.google.gson.Gson;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final Client client;

    static {
        client = new Client();
    }

    private final int serverPort = 12222;
    private final Gson gson;
    private Socket socket;

    private Client() {
        gson = new Gson();
        try {
            socket = new Socket("127.0.0.1", serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerMessage sendRequest(ClientMessage request) {
        try {
            return client.send(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeSocket() throws IOException {
        client.socket.close();
    }

    private ServerMessage send(ClientMessage request) throws IOException {
        request.setAuthToken(MainFX.getInstance().getToken());
        if (request.getType().equals("logout")) MainFX.getInstance().setToken("");
        String message = gson.toJson(request);
        if (!socket.isConnected()) socket = new Socket("127.0.0.1", serverPort);
        sendRequest(message);
        return gson.fromJson(getAnswer(), ServerMessage.class);
    }

    private void sendRequest(String message) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        while (message.length() > 1000) {
            dataOutputStream.writeUTF("wait" + message.substring(0, 1000));
            message = message.substring(1000);
        }
        dataOutputStream.writeUTF(message);
    }

    private String getAnswer() throws IOException {
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
