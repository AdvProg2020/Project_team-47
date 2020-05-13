package view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class CreateManagerProfileCommand extends Command {
    public CreateManagerProfileCommand(Menu menu) {
        super(menu);
        setSignature("create manager profile");
        setRegex("^create manager profile$");
    }

    @Override
    public void doCommand(String text) {

    }
}
