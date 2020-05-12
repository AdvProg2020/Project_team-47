package view.menu.User.customer.subMenus.viewCartMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.User.customer.commands.ViewCartCommand;
import view.menu.User.customer.subMenus.viewCartMenu.commands.*;

public class ViewCartMenu extends Menu {
    public ViewCartMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    /*@Override
    public void execute() {

    }*/

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ShowProductsInCartCommand(this));
        menuCommands.add(new ViewProductInCart(this));
        menuCommands.add(new IncreaseProductInCart(this));
        menuCommands.add(new DecreaseProductInCart(this));
        menuCommands.add(new ShowTotalPriceOfCart(this));
        menuCommands.add(new PurchaseCart(this));
        menuCommands.add(new BackCommand(this));
        menuCommands.add(new HelpCommand(this));
    }
}
