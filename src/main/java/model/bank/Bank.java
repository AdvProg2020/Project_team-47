package model.bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Bank {
    private static Bank bank;
    private final ArrayList<Account> accounts;
    private final ArrayList<Token> tokens;
    private final ArrayList<Receipt> receipts;
    public Bank() {
        accounts = new ArrayList<>();
        tokens = new ArrayList<>();
        receipts = new ArrayList<>();
        new Thread(() -> {
            try {
                waitForConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }

    private void waitForConnection() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        Socket clientSocket;
        while (true) {
            try {
                System.out.println("bank: Waiting for a client...");
                clientSocket = serverSocket.accept();
                System.out.println("bank:A client Connected!");
                goToClientHandler(clientSocket);
            } catch (Exception e) {
                System.err.println("bank:Error in accepting client!");
                break;
            }
        }
    }

    private void goToClientHandler(Socket clientSocket) throws IOException {
        new Thread(() -> {
            try {
                handleClient(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) throws IOException {

        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

        try {
            String input = dataInputStream.readUTF();
            //todo amir

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public Token findTokenWithId(int tokenId) {
        for (Token token : tokens) {
            if (token.getTokenId() == tokenId) {
                return token;
            }
        }
        return null;
    }

    public boolean isUsernameAvailable(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean isIdValid(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordCorrect(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTokenValid(int tokenId) {
        for (Token token : tokens) {
            if (token.getTokenId() == tokenId) {
                return true;
            }
        }
        return false;
    }

    public Account findAccountWithId(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }
}
