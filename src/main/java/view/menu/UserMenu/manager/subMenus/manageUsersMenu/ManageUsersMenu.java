package view.menu.UserMenu.manager.subMenus.manageUsersMenu;

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
        new ViewUserCommand(this);
        new DeleteUserCommand(this);
        new CreateManagerProfileCommand(this);

    }
}
