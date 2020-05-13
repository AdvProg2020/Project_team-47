package view.menu.UserMenu.manager.subMenus.manageAllProductsMenu;

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
        new RemoveProductCommand(this);
    }
}
