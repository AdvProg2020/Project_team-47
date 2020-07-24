package controller.Bank;

import controller.Controller;
import controller.login.LoginCommands;
import controller.login.LoginController;

import java.util.ArrayList;

public class BankController extends Controller {
    private static BankController bankController;


    private BankController() {
        commands = new ArrayList<>();
        commands.add(BankCommands.getCreateAccountCommand());
        commands.add(BankCommands.getCreateReceiptCommand());
        commands.add(BankCommands.getExitCommand());
        commands.add(BankCommands.getBalanceCommand());
        commands.add(BankCommands.getTokenCommand());
        commands.add(BankCommands.getTransactionsCommand());
        commands.add(BankCommands.getPayCommand());
    }


    public static Controller getInstance() {
        if (bankController != null)
            return bankController;
        bankController = new BankController();
        return bankController;
    }
}
