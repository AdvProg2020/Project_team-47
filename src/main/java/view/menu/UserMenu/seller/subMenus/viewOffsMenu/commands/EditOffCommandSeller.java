package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class EditOffCommandSeller extends Command {
    public EditOffCommandSeller(Menu menu) {
        super(menu);
        setSignature("edit [offId]");
        setRegex("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
