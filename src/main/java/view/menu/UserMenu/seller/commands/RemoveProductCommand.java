package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class RemoveProductCommand extends Command {
    public RemoveProductCommand(Menu menu) {
        super(menu);
        setSignature("remove product [productId]");
        setRegex("^remove product [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
