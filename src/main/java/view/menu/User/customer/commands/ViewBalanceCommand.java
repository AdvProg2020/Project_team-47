package view.menu.User.customer.commands;

import view.UserAttributes;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

public class ViewBalanceCommand extends Command {
    public ViewBalanceCommand(Menu menu) {
        super(menu);
        setSignature("view balance");
        setRegex("^view balance$");
    }

    @Override
    public void doCommand(String text) {
        if (!UserAttributes.isSignedIn()){
            OutputErrors.notSignedIn();
        } else {
            UserAttributes.setMoney(getBalanceFromController());
        }
    }

    private double getBalanceFromController() {
        return 0.0;
    }
}
