package view.menu.productMenu.subMenus.digestMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class AddProductToCartCommand extends Command {
    public AddProductToCartCommand(Menu menu) {
        super(menu);
        setSignature("add to cart");
        setRegex("^add to cart$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
