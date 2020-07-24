package controller;

import controller.Bank.BankCommand;
import model.ecxeption.Bank.BankException;
import model.ecxeption.Exception;

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
        } catch (IOException | BankException e) {
            e.printStackTrace();
        }
    }

    private void handleRequests() throws IOException, Exception {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(storeSocket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(storeSocket.getInputStream()));
        while (true) {
            String input = dataInputStream.readUTF();
            BankCommand command = findCommand(input);
            if (command == null) {
                throw new BankException.InvalidInputException();
            }
            String answer = command.process(input);
            if (answer.equals("exit")) {
                waitForClient();
            } else {
                dataOutputStream.writeUTF(answer);
            }
        }
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


