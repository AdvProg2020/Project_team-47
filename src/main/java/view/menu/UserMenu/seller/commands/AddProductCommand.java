package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class AddProductCommand extends Command {
    public AddProductCommand(Menu menu) {
        super(menu);
        setSignature("add product");
        setRegex("^add product$");
    }

    @Override
    public void doCommand(String text) {

    }
}
