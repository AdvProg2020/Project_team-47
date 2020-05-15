package view.menu.UserMenu.manager.subMenus.manageRequestsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands.AcceptRequestCommand;
import view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands.DeclineRequestCommand;
import view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands.RequestDetailsCommand;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("manage requests menu");
    }


    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new RequestDetailsCommand(this));
        menuCommands.add(new AcceptRequestCommand(this));
        menuCommands.add(new DeclineRequestCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
