package bank;

import com.google.gson.Gson;
import controller.Controller;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class StoreToBankConnection {

    public static void main(String[]args){
        try {
            getInstance().getToken("1aliUsername", "1aliPassword");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static StoreToBankConnection storeToBankConnection;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public static StoreToBankConnection getInstance() {
        if (storeToBankConnection != null) {
            return storeToBankConnection;
        }
        storeToBankConnection = new StoreToBankConnection();
        return storeToBankConnection;
    }

    public StoreToBankConnection() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), Controller.getBankPort());
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ServerMessage doAction(String inputType) throws IOException {
        ClientMessage request = new ClientMessage();
        request.setType(inputType);
        sendRequest(request);
        return getAnswer();
    }

    private void sendRequest(ClientMessage request) throws IOException {
        dataOutputStream.writeUTF(new Gson().toJson(request, ClientMessage.class));
        dataOutputStream.flush();
    }

    private ServerMessage getAnswer() throws IOException {
        String input = dataInputStream.readUTF();
        return new Gson().fromJson(input, ServerMessage.class);
    }

    public ServerMessage createAccount(String firstName, String lastName, String username, String password, String repeatPassword) throws IOException {
        return doAction("create_account " + firstName + " " + lastName
                + " " + username + " " + password + " " + repeatPassword);
    }

    public ServerMessage getToken(String username, String password) throws IOException {
        return doAction("get_token " + username + " " + password);
    }

    public ServerMessage createReceipt(String tokenId, String receiptType, String money, String sourceId, String destId) throws IOException {
        return doAction("create_receipt " + tokenId + " " + receiptType
                + " " + money + " " + sourceId + " " + destId);
    }

    public ServerMessage getTransactions(String tokenId, String type) throws IOException {
        return doAction("get_transactions " + tokenId + " " + type);
    }

    public ServerMessage pay(String receiptId) throws IOException {
        return doAction("pay " + receiptId);
    }

    public ServerMessage getBalance(String tokenId) throws IOException {
        return doAction("get_balance " + tokenId);
    }

    public ServerMessage exit() throws IOException {
        return doAction("exit");
    }

}
