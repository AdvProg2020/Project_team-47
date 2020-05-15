package view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class AcceptRequestCommand extends Command {
    public AcceptRequestCommand(Menu menu) {
        super(menu);
        setSignature("accept [requestId]");
        setRegex("^accept [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        
    }
}
