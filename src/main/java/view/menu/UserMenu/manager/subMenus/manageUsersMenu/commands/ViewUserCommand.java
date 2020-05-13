package view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewUserCommand extends Command {
    public ViewUserCommand(Menu menu) {
        super(menu);
        setSignature("view [username]");
        setRegex("^view [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
