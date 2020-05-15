package view.menu.UserMenu.manager.subMenus.manageUsersMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands.CreateManagerProfileCommand;
import view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands.DeleteUserCommand;
import view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands.ViewUserCommand;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu previousMenu) {
        super(previousMenu);
        setName("manage users menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewUserCommand(this));
        menuCommands.add(new DeleteUserCommand(this));
        menuCommands.add(new CreateManagerProfileCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));

    }
}
