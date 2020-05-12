package view.menu.User.customer;

import view.command.ExitCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.User.PersonalInfoMenu;
import view.menu.User.commands.ViewPersonalInfoCommand;
import view.menu.User.commands.LoginAndRegisterCommandForUser;
import view.menu.User.customer.commands.*;
import view.menu.User.customer.subMenus.ordersMenu.OrdersMenu;
import view.menu.User.customer.subMenus.viewCartMenu.ViewCartMenu;
import view.menu.loginAndRegister.LoginAndRegisterMenu;

public class CustomerPanelMenu extends Menu {
    public CustomerPanelMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {
        subMenus.add(new OrdersMenu("orders menu", this));
        subMenus.add(new ViewCartMenu("view cart menu", this));
        subMenus.add(new PersonalInfoMenu("personal info menu", this));
        subMenus.add(new LoginAndRegisterMenu("login/register menu", this));
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewPersonalInfoCommand(this));
        menuCommands.add(new ViewCartCommand(this));
        menuCommands.add(new ViewOrdersCommand(this));
        menuCommands.add(new ViewBalanceCommand(this));
        menuCommands.add(new ViewDiscountCodesCommand(this));
        menuCommands.add(new LoginAndRegisterCommandForUser(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new ExitCommand(this));
    }
}
