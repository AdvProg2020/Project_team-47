package view.menu.User.manager;

import view.command.ExitCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.User.commands.ViewPersonalInfoCommand;
import view.menu.User.manager.Commands.*;

public class ManagerPanelMenu extends Menu {
    public ManagerPanelMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewPersonalInfoCommand(this));
        menuCommands.add(new ManageUsersCommand(this));
        menuCommands.add(new ManageAllProductsCommand(this));
        menuCommands.add(new CreateDiscountCodesCommand(this));
        menuCommands.add(new ViewDiscountCodesCommand(this));
        menuCommands.add(new ManageRequestsCommand(this));
        menuCommands.add(new ManageCategoriesCommand(this));
        menuCommands.add(new ExitCommand(this));
        menuCommands.add(new HelpCommand(this));

    }
}
