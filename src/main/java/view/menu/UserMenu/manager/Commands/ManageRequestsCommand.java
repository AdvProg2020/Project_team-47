package view.menu.UserMenu.manager.Commands;

import view.command.Command;
import view.menu.Menu;

public class ManageRequestsCommand extends Command {
    public ManageRequestsCommand(Menu menu) {
        super(menu);
        setSignature("manage requests");
        setRegex("^manage requests$");
    }

    @Override
    public void doCommand(String text) {

    }
}
