package view.menu.productMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ProductDigestCommand extends Command {
    public ProductDigestCommand(Menu menu) {
        super(menu);
        setSignature("digest");
        setRegex("^digest$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
