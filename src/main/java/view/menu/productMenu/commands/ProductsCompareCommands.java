package view.menu.productMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ProductsCompareCommands extends Command {
    public ProductsCompareCommands(Menu menu) {
        super(menu);
        setSignature("compare [productID]");
        setRegex("^compare [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
