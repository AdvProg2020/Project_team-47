package bank;

import com.google.gson.Gson;
import controller.PortsAndIps;
import database.Database;
import model.bank.Account;
import model.bank.Bank;
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
    private final ServerSocket bankSocket;
    private Socket storeSocket;
    private ArrayList<BankCommand> bankCommands;
    private ClientMessage request;
    private ServerMessage answer;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public BankServer() throws IOException {
        bankSocket = new ServerSocket(PortsAndIps.BANK_PORT);
        bankServer = this;
        bankCommands = new ArrayList<>();
        addCommands();
        loadBankDataBase();
        if (Bank.getInstance().isUsernameAvailable("apshop47")) {
            Bank.getInstance().getAccounts().add(new Account
                    ("apshop47", "apshop47"
                            , "apshop47", "apshop47"));
        }
        updateBankDataBase();
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
        while (true) {
            String input = dataInputStream.readUTF();
            request = new Gson().fromJson(input, ClientMessage.class);

            BankCommand command = findCommand(request.getType());
            try {
                if (command == null) {
                    System.out.println("invalid input");
                    throw new BankException.InvalidInputException();
                }
                answer = command.process(request);
                if (answer.getFirstString() != null && answer.getFirstString().equals("exit")) {
                    waitForClient();
                } else {
                    System.out.println("Successful");
                    updateBankDataBase();
                    sendAnswer(answer);
                }
            } catch (BankException e) {
                System.out.println("error occurred");
                sendError(e);
            }
        }
    }

    private void sendError(BankException e) throws IOException {
        answer.setType("Error");
        answer.setErrorMessage(e.getMessage());
        dataOutputStream.writeUTF(new Gson().toJson(answer, ServerMessage.class));
        dataOutputStream.flush();
    }

    private void sendAnswer(ServerMessage answer) throws IOException {
        answer.setType("Successful");
        dataOutputStream.writeUTF(new Gson().toJson(answer, ServerMessage.class));
        dataOutputStream.flush();
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

    private static void updateBankDataBase() {
        Database.updateBankAccounts(Bank.getInstance().getAccounts());
        Database.updateBankTokens(Bank.getInstance().getTokens());
        Database.updateBankReceipts(Bank.getInstance().getReceipts());
        Database.updateBankTransactions(Bank.getInstance().getTransactions());
    }

    private static void loadBankDataBase() {
        Database.loadBankAccounts();
        Database.loadBankReceipts();
        Database.loadBankTokens();
        Database.loadBankTransactions();
    }

}


