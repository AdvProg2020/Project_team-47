package view.menu.UserMenu.manager.subMenus.manageAllProductsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("manage all products menu");
    }


    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new RemoveProductCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
