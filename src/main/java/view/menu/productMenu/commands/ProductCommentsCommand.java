package view.menu.productMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ProductCommentsCommand extends Command {
    public ProductCommentsCommand(Menu menu) {
        super(menu);
        setSignature("Comments");
        setRegex("^Comments$");
    }

    @Override
    public void doCommand(String text) {

    }
}
