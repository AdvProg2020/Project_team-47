package controller.Bank;

import controller.Command;
import controller.Controller;
import model.bank.Account;
import model.bank.Bank;
import model.bank.Receipt;
import model.bank.Token;
import model.ecxeption.Bank.BankException;
import model.ecxeption.Exception;
import model.ecxeption.user.*;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.User;

import static controller.Controller.*;

public abstract class BankCommands extends Command {
    public static CreateAccountCommand getCreateAccountCommand() {
        return CreateAccountCommand.getInstance();
    }

    public static CreateReceiptCommand getCreateReceiptCommand() {
        return CreateReceiptCommand.getInstance();
    }

    public static ExitCommand getExitCommand() {
        return ExitCommand.getInstance();
    }

    public static GetBalanceCommand getBalanceCommand() {
        return GetBalanceCommand.getInstance();
    }

    public static GetTokenCommand getTokenCommand() {
        return GetTokenCommand.getInstance();
    }

    public static GetTransactionsCommand getTransactionsCommand() {
        return GetTransactionsCommand.getInstance();
    }

    public static PayCommand getPayCommand() {
        return PayCommand.getInstance();
    }
}

class CreateAccountCommand extends BankCommands {
    private static CreateAccountCommand command;

    private CreateAccountCommand() {
        this.name = "create_account \\S+ \\S+ \\S+ \\S+ \\S+";
    }

    public static CreateAccountCommand getInstance() {
        if (command != null)
            return command;
        command = new CreateAccountCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        checkPrimaryErrors(request);
        createAccount(request.getType().split("\\s"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        String[] parameters = request.getType().split("\\s");
        // parameters = create_account firstName lastName username password repeat-password
        if (!parameters[4].equals(parameters[5])) {
            throw new BankException.IncorrectPasswordException();
        }
        if (!usernameAvailable(parameters[3])) {
            throw new BankException.UnavailableUsernameException();
        }
    }

    private boolean usernameAvailable(String username) {
        return Bank.getInstance().getAccounts().stream().noneMatch(x -> x.getUsername().equals(username));
    }
    private void createAccount(String[] parameters) {
        Bank.getInstance().getAccounts().add(new Account(parameters[3],
                parameters[4], parameters[1], parameters[2]));
    }

}//end CreateAccountCommand Class


class CreateReceiptCommand extends BankCommands {
    private static CreateReceiptCommand command;
    private User user;

    private CreateReceiptCommand() {
        this.name = "create_receipt \\d+ \\S+ \\d+ \\d+ \\d+.*";
    }

    public static CreateReceiptCommand getInstance() {
        if (command != null)
            return command;
        command = new CreateReceiptCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        checkPrimaryErrors(request);
        createReceipt(request);
        return actionCompleted();
    }

    private void createReceipt(ClientMessage request) {
        String[] parameters = request.getType().split("\\s");
        Receipt receipt = new Receipt(parameters[2], parameters[1], parameters[3],
                parameters[4], parameters[5], getDescription(request));
        switch (parameters[2]) {
            case "deposit" -> deposit(receipt);
            case "withdraw" -> withdraw(receipt);
            case "move" -> move(receipt);
        }
    }

    private void move(Receipt receipt) {
        //todo amir
    }

    private void withdraw(Receipt receipt) {
        //todo amir
    }

    private void deposit(Receipt receipt) {
        //todo amir
    }

    private String getDescription(ClientMessage request) {
        String[] parameters = request.getType().split("\\s");
        String s = parameters[0] + " " + parameters[1] +
                " " + parameters[2] + " " + parameters[3] +
                " " +parameters[4] + " " + parameters[5] + " ";
        return request.getType().substring(s.length());
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        String[] parameters = name.split("\\s");
        if (!isReceiptTypeValid(parameters[2])) {
            throw new BankException.InvalidReceiptTypeException();
        }
        if (!isMoneyValid(parameters[3])) {
            throw new BankException.InvalidMoneyException();
        }
        if (!invalidParameters()) {
            throw new BankException.InvalidParametersException();
        }
        if (!isTokenValid(parameters[0])) {
            throw new BankException.InvalidTokenException();
        }
        if (isTokenExpired(parameters[0])) {
            throw new BankException.ExpiredTokenException();
        }
        if (!isAccountIdValid(parameters[4])) {
            throw new BankException.InvalidSourceAccountIdException();
        }
        if (!isAccountIdValid(parameters[5])) {
            throw new BankException.InvalidDestAccountIdException();
        }
        if (!isSourceAndDestIdSame(parameters[4], parameters[5])) {
            throw new BankException.SourceAndDestIdSameException();
        }
        if (!isAccountIdValid(parameters[4], parameters[5])) {
            throw new BankException.InvalidAccountIdException();
        }
        if (!inputContainsInvalidCharacter()) {
            throw new BankException.InvalidCharactersException();
        }
    }


    private boolean isReceiptTypeValid(String type) {
        return type.equals("deposit")
                || type.equals("withdraw")
                || type.equals("move");
    }

    private boolean isMoneyValid(String money) {
        try {
            int intMoney = Integer.parseInt(money);
            if (intMoney > 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private boolean invalidParameters() {
        //todo amir
        return true;
    }

    private boolean isTokenValid(String tokenId) {
        return Bank.getInstance().isTokenValid(Integer.parseInt(tokenId));
    }

    private boolean isTokenExpired(String tokenId) {
        Token token = Bank.getInstance().findTokenWithId(Integer.parseInt(tokenId));
        return token.getFinishTime().before(Controller.getCurrentTime());
    }

    private boolean isAccountIdValid(String id) {
        int intId = Integer.parseInt(id);
        return !Bank.getInstance().isUsernameAvailable(id);
    }

    private boolean isSourceAndDestIdSame(String source, String dest) {
        return source.equals(dest);
    }

    private boolean isAccountIdValid(String source, String dest) {
        return !source.equals("-1") && !dest.equals("-1");
    }

    private boolean inputContainsInvalidCharacter() {
        //todo amir
        return true;
    }

}//end CreateReceiptCommand Class


class ExitCommand extends BankCommands {
    private static ExitCommand command;

    private ExitCommand() {
        this.name = "exit";
    }


    public static ExitCommand getInstance() {
        if (command != null)
            return command;
        command = new ExitCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        //todo amir
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws ConfirmException, UserNotExistException, WrongPasswordException {

    }

}//end ExitCommand Class


class GetBalanceCommand extends BankCommands {
    private static GetBalanceCommand command;

    private GetBalanceCommand() {
        this.name = "get balance \\S+";
    }

    public static GetBalanceCommand getInstance() {
        if (command != null)
            return command;
        command = new GetBalanceCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        checkPrimaryErrors(request);
        getBalance(request.getType().split("\\s")[1]);
        return actionCompleted();
    }

    private void getBalance(String tokenId) {
        //todo amir
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        String token = name.split("\\s")[1];
        if (!isTokenValid(token)) {
            throw new BankException.InvalidTokenException();
        }
        if (isTokenExpired(token)) {
            throw new BankException.ExpiredTokenException();
        }
    }

    private boolean isTokenExpired(String token) {
        //todo amir
        return true;
    }

    private boolean isTokenValid(String token) {
        //todo amir
        return true;
    }


}//end GetBalanceCommand Class


class GetTokenCommand extends BankCommands {
    private static GetTokenCommand command;
    private User user;

    private GetTokenCommand() {
        this.name = "get token \\S+ \\S+";
    }


    public static GetTokenCommand getInstance() {
        if (command != null)
            return command;
        command = new GetTokenCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        checkPrimaryErrors(request);
        getToken(request);
        return actionCompleted();
    }

    private void getToken(ClientMessage request) {
        String[] parameters = request.getType().split("\\s");
        new Token(parameters[1], parameters[2]);
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        String[] parameters = request.getType().split("\\s");
        if (Bank.getInstance().isUsernameAvailable(parameters[1])) {
            throw new BankException.InvalidUsernameOrPasswordException();
        }
        if (!Bank.getInstance().isPasswordCorrect(parameters[1], parameters[2])) {
            throw new BankException.InvalidUsernameOrPasswordException();
        }
    }

}//end GetTokenCommand Class


class GetTransactionsCommand extends BankCommands {
    private static GetTransactionsCommand command;

    private GetTransactionsCommand() {
        this.name = "get transactions \\S+ \\S+";
    }


    public static GetTransactionsCommand getInstance() {
        if (command != null)
            return command;
        command = new GetTransactionsCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws BankException {
        checkPrimaryErrors(request);
        getTransactions(request);
        return actionCompleted();
    }

    private void getTransactions(ClientMessage request) {
        //todo amir
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws BankException {
        String[] parameters = request.getType().split("\\s");
        if (!isTokenValid(parameters[1])) {
            throw new BankException.InvalidTokenException();
        }
        if (isTokenExpired(parameters[1])) {
            throw new BankException.ExpiredTokenException();
        }
        if (!isReceiptIdValid()) {
            throw new BankException.InvalidReceiptIdException();
        }
    }

    private boolean isTokenValid(String parameter) {
        //todo amir
        return true;
    }

    private boolean isTokenExpired(String parameter) {
        //todo amir
        return true;
    }

    private boolean isReceiptIdValid() {
        //todo amir
        return true;
    }


}//end GetTransactionsCommand Class

class PayCommand extends BankCommands {
    private static PayCommand command;

    private PayCommand() {
        this.name = "pay \\S+";
    }


    public static PayCommand getInstance() {
        if (command != null)
            return command;
        command = new PayCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws BankException {
        checkPrimaryErrors(request);
        pay(request);
        return actionCompleted();
    }

    private void pay(ClientMessage request) {
        //todo amir
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws BankException {
        if (!isReceiptIdValid()) {
            throw new BankException.InvalidReceiptIdException();
        }
        if (isReceiptPaid()) {
            throw new BankException.PaidReceiptException();
        }
        if (!isMoneyEnough()) {
            throw new BankException.InsufficientMoneyException();
        }
        if (isAccountIdValid()) {
            throw new BankException.InvalidAccountIdException();
        }
    }

    private boolean isReceiptIdValid() {
        //todo amir
        return true;
    }

    private boolean isReceiptPaid() {
        //todo amir
        return true;
    }

    private boolean isMoneyEnough() {
        //todo amir
        return true;
    }

    private boolean isAccountIdValid() {
        //todo amir
        return true;
    }

}//end payCommand Class
