package view.menu.UserMenu.manager.subMenus.manageAllProductsMenu;

import view.command.Command;
import view.menu.Menu;

public class RemoveProductCommand extends Command {
    public RemoveProductCommand(Menu menu) {
        super(menu);
        setSignature("remove [productId]");
        this.setRegex("^remove [^\\s]+$");

    }

    @Override
    public void doCommand(String text) {

    }
}
