package view.menu.UserMenu.commands;

import view.command.Command;
import view.menu.Menu;

import java.util.Arrays;

public class GoToProductsMenuCommand extends Command {
    public GoToProductsMenuCommand(Menu menu) {
        super(menu);
        setSignature("products");
        setRegex("^products$");
    }

    @Override
    public void doCommand(String text) {
        this.menu.findSubMenuWithName("all products").autoExecute();
    }
}
