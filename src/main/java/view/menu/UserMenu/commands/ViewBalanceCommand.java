package view.menu.UserMenu.commands;

import view.ViewAttributes;
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
        if (!ViewAttributes.isUserSignedIn()){
            OutputErrors.notSignedIn();
        } else {
            ViewAttributes.setUserMoney(getBalanceFromController());
        }
    }

    private double getBalanceFromController() {
        return 0.0;
    }
}
