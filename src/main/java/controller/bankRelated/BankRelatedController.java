package controller.bankRelated;

import controller.Controller;

import java.util.ArrayList;

public class BankRelatedController extends Controller{
    private static BankRelatedController bankRelatedController;


    private BankRelatedController() {
        commands = new ArrayList<>();
        commands.add(BankRelatedCommands.getRaiseMoneyCommand());
        commands.add(BankRelatedCommands.getLowerWalletMoneyCommand());
    }


    public static Controller getInstance() {
        if (bankRelatedController != null)
            return bankRelatedController;
        bankRelatedController = new BankRelatedController();
        return bankRelatedController;
    }
}
