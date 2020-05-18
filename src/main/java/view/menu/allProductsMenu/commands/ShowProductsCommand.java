package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowProductsCommand extends Command {
    public ShowProductsCommand(Menu menu) {
        super(menu);
        setSignature("show products");
        setRegex("^show products$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
