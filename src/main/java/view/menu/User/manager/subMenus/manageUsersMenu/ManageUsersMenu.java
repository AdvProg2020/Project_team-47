package view.menu.User.manager.subMenus.manageUsersMenu;

import view.menu.Menu;
import view.menu.User.manager.Commands.CreateManagerProfileCommand;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(String name, Menu previousMenu, Menu parentMenu) {
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
        new ViewUserCommand(this);
        new DeleteUserCommand(this);
        new CreateManagerProfileCommand(this);

    }
}
