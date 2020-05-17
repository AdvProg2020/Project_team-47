package view.menu.UserMenu.seller.subMenus.sellerProductsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands.EditSellerProductCommand;
import view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands.ViewSellerProductBuyersCommand;
import view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands.ViewSellerProductCommand;

public class SellerProductsMenu extends Menu {
    public SellerProductsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("seller products menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewSellerProductCommand(this));
        menuCommands.add(new ViewSellerProductBuyersCommand(this));
        menuCommands.add(new EditSellerProductCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
