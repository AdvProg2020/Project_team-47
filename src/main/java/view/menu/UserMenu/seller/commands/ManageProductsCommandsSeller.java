package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class ManageProductsCommandsSeller extends Command {
    public ManageProductsCommandsSeller(Menu menu) {
        super(menu);
        setSignature("manage products");
        setRegex("^manage products$");
    }

    @Override
    public void doCommand(String text) {

    }
}
