package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowProductCommand extends Command {
    public ShowProductCommand(Menu menu) {
        super(menu);
        setSignature("show product [productId]");
        setRegex("^show product [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
