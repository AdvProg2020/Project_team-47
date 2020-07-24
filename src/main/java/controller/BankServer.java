package controller;

import com.google.gson.Gson;
import controller.Bank.BankCommand;
import model.ecxeption.Bank.BankException;
import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BankServer {
    private static BankServer bankServer;
    private final int port = 12223;
    private final ServerSocket bankSocket;
    private Socket storeSocket;
    private ArrayList<BankCommand> bankCommands;
    private ClientMessage request;
    private ServerMessage answer;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public BankServer() throws IOException {
        bankSocket = new ServerSocket(port);
        bankServer = this;
        bankCommands = new ArrayList<>();
        addCommands();
    }

    private void addCommands() {
        bankCommands.add(BankCommand.getCreateAccountCommand());
        bankCommands.add(BankCommand.getCreateReceiptCommand());
        bankCommands.add(BankCommand.getBalanceCommand());
        bankCommands.add(BankCommand.getExitCommand());
        bankCommands.add(BankCommand.getPayCommand());
        bankCommands.add(BankCommand.getTokenCommand());
        bankCommands.add(BankCommand.getTransactionsCommand());
    }

    public static BankServer getBankServer() {
        return bankServer;
    }

    public static void main(String[] args) throws IOException {
        try {
            new BankServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws Exception {
        try {
            storeSocket = bankSocket.accept();
            handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequests() throws IOException {
         dataOutputStream = new DataOutputStream(new BufferedOutputStream(storeSocket.getOutputStream()));
         dataInputStream = new DataInputStream(new BufferedInputStream(storeSocket.getInputStream()));
         answer = new ServerMessage();
        while (true) {
            String input = dataInputStream.readUTF();
            request = new Gson().fromJson(input, ClientMessage.class);

            BankCommand command = findCommand(request.getType());
            try {
                if (command == null) {
                    throw new BankException.InvalidInputException();
                }
                answer = command.process(request);
                if (answer.getFirstString().equals("exit")) {
                    waitForClient();
                } else {
                    sendAnswer(answer);
                }
            } catch (BankException e) {
                sendError(e);
            }
        }
    }

    private void sendError(BankException e) throws IOException {
        answer.setType("error");
        answer.setErrorMessage(e.getMessage());
        dataOutputStream.writeUTF(new Gson().toJson(answer, ServerMessage.class));
    }

    private void sendAnswer(ServerMessage answer) throws IOException {
        answer.setType("successful");
        dataOutputStream.writeUTF(new Gson().toJson(answer, ServerMessage.class));
    }

    private void waitForClient() {
        try {
            storeSocket = new Socket();
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BankCommand findCommand(String input) {
        for (BankCommand command : bankCommands) {
            if (Pattern.compile(command.getName()).matcher(input).find()) {
                return command;
            }
        }
        return null;
    }

}


