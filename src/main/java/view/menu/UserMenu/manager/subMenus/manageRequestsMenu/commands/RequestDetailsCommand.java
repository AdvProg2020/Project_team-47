package view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class RequestDetailsCommand extends Command {
    public RequestDetailsCommand(Menu menu) {
        super(menu);
        setSignature("details [requestId]");
        setRegex("^details [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
