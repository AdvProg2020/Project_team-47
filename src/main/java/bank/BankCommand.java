package bank;

import controller.Controller;
import model.bank.*;
import model.ecxeption.Bank.BankException;
import model.ecxeption.Exception;
import model.ecxeption.user.*;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;

public abstract class BankCommand {
    protected String name;
    protected static ServerMessage serverMessage;

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

    public abstract ServerMessage process(ClientMessage request) throws BankException;

    public abstract void checkPrimaryErrors(String request) throws Exception;

    public String getName() {
        return name;
    }

    public static ServerMessage actionCompleted() {
        serverMessage.setType("Successful");
        return serverMessage;
    }

}

class CreateAccountCommand extends BankCommand {
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
    public ServerMessage process(ClientMessage request) throws BankException {
        checkPrimaryErrors(request.getType());
        createAccount(request.getType().split("\\s"));
        serverMessage = new ServerMessage();
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(String request) throws BankException {
        String[] parameters = request.split("\\s");
        // parameters = create_account firstName lastName username password repeat-password
        if (!parameters[4].equals(parameters[5])) {
            throw new BankException.IncorrectPasswordException();
        }
        if (!usernameAvailable(parameters[3])) {
            throw new BankException.UnavailableUsernameException();
        }
    }

    private boolean usernameAvailable(String username) {
        return Bank.getInstance().getAccounts().stream().noneMatch(x -> x.getId().equals(username));
    }
    private void createAccount(String[] parameters) {
        Bank.getInstance().getAccounts().add(new Account(parameters[3],
                parameters[4], parameters[1], parameters[2]));
    }

}//end CreateAccountCommand Class


class CreateReceiptCommand extends BankCommand {
    private static CreateReceiptCommand command;

    private CreateReceiptCommand() {
        this.name = "create_receipt \\d+ \\S+ \\d+ \\S+ \\S+";
    }

    public static CreateReceiptCommand getInstance() {
        if (command != null)
            return command;
        command = new CreateReceiptCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws BankException {
        checkPrimaryErrors(request.getType());
        createReceipt(request.getType());
        return actionCompleted();
    }

    private void createReceipt(String request) {
        String[] parameters = request.split("\\s");
        Receipt receipt = new Receipt(parameters[2], parameters[1], parameters[3],
                parameters[4], parameters[5], getDescription(request));
        serverMessage = new ServerMessage();
        serverMessage.setReceipt(receipt);
    }



    private String getDescription(String request) {
        String[] parameters = request.split("\\s");
        String s = parameters[0] + " " + parameters[1] +
                " " + parameters[2] + " " + parameters[3] +
                " " +parameters[4] + " " + parameters[5] + " ";
        return request.substring(s.length());
    }

    @Override
    public void checkPrimaryErrors(String request) throws BankException {
        String[] parameters = request.split("\\s");
        if (!isReceiptTypeValid(parameters[2])) {
            System.out.println(parameters[2]);
            throw new BankException.InvalidReceiptTypeException();
        }
        if (!isMoneyValid(parameters[3])) {
            throw new BankException.InvalidMoneyException();
        }
        if (!invalidParameters()) {
            throw new BankException.InvalidParametersException();
        }
        if (!isTokenValid(parameters[1])) {
            throw new BankException.InvalidTokenException();
        }
        if (isTokenExpired(parameters[1])) {
            throw new BankException.ExpiredTokenException();
        }
        if (!isSourceOrDestAccountIdValid(parameters[4])) {
            System.out.println(parameters[4]);
            throw new BankException.InvalidSourceAccountIdException();
        }
        if (!isSourceOrDestAccountIdValid(parameters[5])) {
            System.out.println(parameters[6]);
            throw new BankException.InvalidDestAccountIdException();
        }
        if (isSourceAndDestIdSame(parameters[4], parameters[5]) && parameters[2].equals("move")) {
            throw new BankException.SourceAndDestIdSameException();
        }
        if (!isAccountsIdsValid(parameters[4], parameters[5])) {
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
        System.out.println(tokenId);
        return Bank.getInstance().isTokenValid(Integer.parseInt(tokenId));
    }

    private boolean isTokenExpired(String tokenId) {
        Token token = Bank.getInstance().findTokenWithId(Integer.parseInt(tokenId));
        return token.isExpired();
    }

    private boolean isSourceOrDestAccountIdValid(String id) {
        return Bank.getInstance().isIdValid(id);
    }

    private boolean isSourceAndDestIdSame(String source, String dest) {
        return source.equals(dest);
    }

    private boolean isAccountsIdsValid(String source, String dest) {
        return !source.equals("-1") && !dest.equals("-1");
    }

    private boolean inputContainsInvalidCharacter() {
        //todo amir
        return true;
    }

}//end CreateReceiptCommand Class


class ExitCommand extends BankCommand {
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
    public ServerMessage process(ClientMessage request) throws BankException {
        ServerMessage answer = new ServerMessage();
        answer.setFirstString("exit");
        return answer;
    }

    @Override
    public void checkPrimaryErrors(String request) throws ConfirmException, UserNotExistException, WrongPasswordException {

    }

}//end ExitCommand Class


class GetBalanceCommand extends BankCommand {
    private static GetBalanceCommand command;

    private GetBalanceCommand() {
        this.name = "get_balance \\S+";
    }

    public static GetBalanceCommand getInstance() {
        if (command != null)
            return command;
        command = new GetBalanceCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws BankException {
        checkPrimaryErrors(request.getType());
        serverMessage = new ServerMessage();
        serverMessage.setFirstString("" + getBalance(request.getType().split("\\s")[1]));
        return actionCompleted();
    }

    private double getBalance(String tokenId) {
        int intTokenId = Integer.parseInt(tokenId);
        return Bank.getInstance().findTokenWithId(intTokenId).getAccount().getMoney();
    }

    @Override
    public void checkPrimaryErrors(String request) throws BankException {
        String token = request.split("\\s")[1];
        if (!isTokenValid(token)) {
            throw new BankException.InvalidTokenException();
        }
        if (isTokenExpired(token)) {
            throw new BankException.ExpiredTokenException();
        }
    }

    private boolean isTokenValid(String tokenId) {
        return Bank.getInstance().isTokenValid(Integer.parseInt(tokenId));
    }

    private boolean isTokenExpired(String tokenId) {
        Token token = Bank.getInstance().findTokenWithId(Integer.parseInt(tokenId));
        return token.getFinishTime().before(Controller.getCurrentTime());
    }


}//end GetBalanceCommand Class


class GetTokenCommand extends BankCommand {
    private static GetTokenCommand command;

    private GetTokenCommand() {
        this.name = "get_token \\S+ \\S+";
    }


    public static GetTokenCommand getInstance() {
        if (command != null)
            return command;
        command = new GetTokenCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws BankException {

        checkPrimaryErrors(request.getType());
        getToken(request.getType());
        serverMessage = new ServerMessage();
        serverMessage.setToken(getToken(request.getType()));
        return actionCompleted();
    }

    private Token getToken(String request) {
        String[] parameters = request.split("\\s");
        return new Token(parameters[1], parameters[2]);
    }

    @Override
    public void checkPrimaryErrors(String request) throws BankException {
        String[] parameters = request.split("\\s");
        if (Bank.getInstance().isUsernameAvailable(parameters[1])) {
            throw new BankException.InvalidUsernameOrPasswordException();
        }
        if (!Bank.getInstance().isPasswordCorrect(parameters[1], parameters[2])) {
            throw new BankException.InvalidUsernameOrPasswordException();
        }
        if(Bank.getInstance().findAccountWithId(parameters[1]).hasToken()){
            throw new BankException.AlreadyHasTokenException();
        }
    }

}//end GetTokenCommand Class


class GetTransactionsCommand extends BankCommand {
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
        checkPrimaryErrors(request.getType());
        ServerMessage answer = new ServerMessage();
        answer.setTransactions(getTransactions(request.getType()));
        return answer;
    }

    private ArrayList<Transaction> getTransactions(String request) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        //todo amir
        return transactions;
    }

    @Override
    public void checkPrimaryErrors(String request) throws BankException {
        String[] parameters = request.split("\\s");
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

    private boolean isTokenValid(String tokenId) {
        return Bank.getInstance().isTokenValid(Integer.parseInt(tokenId));
    }

    private boolean isTokenExpired(String tokenId) {
        Token token = Bank.getInstance().findTokenWithId(Integer.parseInt(tokenId));
        return token.getFinishTime().before(Controller.getCurrentTime());
    }

    private boolean isReceiptIdValid() {
        //todo amir
        return true;
    }


}//end GetTransactionsCommand Class

class PayCommand extends BankCommand {
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
        checkPrimaryErrors(request.getType());
        pay(request.getType());
        return actionCompleted();
    }

    private void pay(String request) {
        Receipt receipt = Bank.getInstance().findReceiptWithId(Integer.parseInt(request.split("\\s")[1]));
        switch (receipt.getType()) {
            case "deposit" -> deposit(receipt);
            case "withdraw" -> withdraw(receipt);
            case "move" -> move(receipt);
        }
    }

    private void move(Receipt receipt) {
        Account sourceAccount = Bank.getInstance().findAccountWithId(receipt.getSourceId());
        Account destAccount = Bank.getInstance().findAccountWithId(receipt.getDestId());
        sourceAccount.addMoney(-1 * receipt.getMoney());
        destAccount.addMoney(receipt.getMoney());
        receipt.setPaid(true);
    }

    private void withdraw(Receipt receipt) {
        Account sourceAccount = Bank.getInstance().findAccountWithId(receipt.getSourceId());
        sourceAccount.addMoney(-1 * receipt.getMoney());
        receipt.setPaid(true);
    }

    private void deposit(Receipt receipt) {
        Account sourceAccount = Bank.getInstance().findAccountWithId(receipt.getSourceId());
        sourceAccount.addMoney(receipt.getMoney());
        receipt.setPaid(true);
    }

    @Override
    public void checkPrimaryErrors(String request) throws BankException {
        if (!isReceiptIdValid(request)) {
            throw new BankException.InvalidReceiptIdException();
        }
        if (isReceiptPaid(request)) {
            throw new BankException.PaidReceiptException();
        }
        if (!isMoneyEnough(request)) {
            throw new BankException.InsufficientMoneyException();
        }
        if (!isAccountIdValid(request)) {
            System.out.println("salam");
            throw new BankException.InvalidAccountIdException();
        }
    }

    private boolean isReceiptIdValid(String request) {
        int id = Integer.parseInt(request.split("\\s")[1]);
        return Bank.getInstance().isReceiptIdValid(id);
    }

    private boolean isReceiptPaid(String request) {
        int id = Integer.parseInt(request.split("\\s")[1]);
        return Bank.getInstance().findReceiptWithId(id).isPaid();
    }

    private boolean isMoneyEnough(String request) {
        int id = Integer.parseInt(request.split("\\s")[1]);
        Receipt receipt = Bank.getInstance().findReceiptWithId(id);
        Account source = Bank.getInstance().findAccountWithId(receipt.getSourceId());
        return switch (receipt.getType()) {
            case "withdraw", "move" -> source.getMoney() >= receipt.getMoney();
            default -> true;
        };
    }

    private boolean isAccountIdValid(String request) {
        //todo amir
        return true;
    }

}//end payCommand Class
