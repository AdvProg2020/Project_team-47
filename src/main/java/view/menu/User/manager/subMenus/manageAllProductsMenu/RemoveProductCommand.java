package view.menu.User.manager.subMenus.manageAllProductsMenu;

import view.command.Command;
import view.menu.Menu;

public class RemoveProductCommand extends Command {
    public RemoveProductCommand(Menu menu) {
        super(menu);
        this.setRegex("^remove ([^ ]+)$");

    }

    @Override
    public void doCommand(String text) {

    }
}
