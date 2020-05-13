package view.menu.UserMenu.manager.Commands;

import view.command.Command;
import view.menu.Menu;

public class ManageUsersCommand extends Command {
    public ManageUsersCommand(Menu menu) {
        super(menu);
        setSignature("manage users");
        setRegex("^manage users$");
    }

    @Override
    public void doCommand(String text) {

    }
}
