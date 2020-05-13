package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewOffsCommand extends Command {
    public ViewOffsCommand(Menu menu) {
        super(menu);
        setSignature("view offs");
        setRegex("^view offs$");
    }

    @Override
    public void doCommand(String text) {
    }
}
