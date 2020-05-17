package view.menu.UserMenu.customer;

import view.command.ExitCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.PersonalInfoMenu;
import view.menu.UserMenu.commands.*;
import view.menu.UserMenu.customer.commands.*;
import view.menu.UserMenu.customer.subMenus.ordersMenu.OrdersMenu;
import view.menu.UserMenu.customer.subMenus.viewCartMenu.ViewCartMenu;
import view.menu.allProductsMenu.AllProductsMenu;
import view.menu.loginAndRegisterMenu.LoginAndRegisterMenu;
import view.menu.offsMenu.AllOffsMenu;

public class CustomerPanelMenu extends Menu {
    public CustomerPanelMenu(Menu previousMenu) {
        super(previousMenu);
        setName("customer panel menu");
    }

    @Override
    protected void setSubMenus() {
        subMenus.add(new OrdersMenu( this));
        subMenus.add(new ViewCartMenu( this));
        subMenus.add(new PersonalInfoMenu(this));
        subMenus.add(new LoginAndRegisterMenu(this));
        subMenus.add(new AllOffsMenu(this));
        subMenus.add(new AllProductsMenu(this));
        subMenus.add(new AllOffsMenu(this));
        subMenus.add(new PersonalInfoMenu(this));
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewPersonalInfoCommand(this));
        menuCommands.add(new GoToOffsMenuCommand(this));
        menuCommands.add(new GoToProductsMenuCommand(this));
        menuCommands.add(new ViewCartCommand(this));
        menuCommands.add(new ViewOrdersCommand(this));
        menuCommands.add(new ViewBalanceCommandCustomer(this));
        menuCommands.add(new ViewDiscountCodesCommand(this));
        menuCommands.add(new LoginAndRegisterCommandForUser(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new ExitCommand(this));
    }
}
