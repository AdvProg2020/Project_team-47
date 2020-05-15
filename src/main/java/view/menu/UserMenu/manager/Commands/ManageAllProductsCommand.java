package view.menu.UserMenu.manager.Commands;

import view.command.Command;
import view.menu.Menu;

public class ManageAllProductsCommand extends Command {
    public ManageAllProductsCommand(Menu menu) {
        super(menu);
        setSignature("manage all products");
        setRegex("^manage all products$");
    }

    @Override
    public void doCommand(String text) {
        this.menu.findSubMenuWithName("manage all products menu").autoExecute();
    }
}
