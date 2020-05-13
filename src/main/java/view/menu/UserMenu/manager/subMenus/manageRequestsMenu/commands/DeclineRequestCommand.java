package view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class DeclineRequestCommand extends Command {
    public DeclineRequestCommand(Menu menu) {
        super(menu);
        setSignature("decline [requestId]");
        setRegex("^decline [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
