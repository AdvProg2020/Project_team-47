package bank;

import controller.Controller;

public class BankController extends Controller {
    private static BankController bankController;


    private BankController() {
        /*commands = new ArrayList<>();
        commands.add(BankCommand.getCreateAccountCommand());
        commands.add(BankCommand.getCreateReceiptCommand());
        commands.add(BankCommand.getExitCommand());
        commands.add(BankCommand.getBalanceCommand());
        commands.add(BankCommand.getTokenCommand());
        commands.add(BankCommand.getTransactionsCommand());
        commands.add(BankCommand.getPayCommand());*/
    }


    public static Controller getInstance() {
        if (bankController != null)
            return bankController;
        bankController = new BankController();
        return bankController;
    }
}
