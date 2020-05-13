package view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class DeleteUserCommand extends Command {
    public DeleteUserCommand(Menu menu) {
        super(menu);
        setSignature("delete user [username]");
        setRegex("^delete user [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
