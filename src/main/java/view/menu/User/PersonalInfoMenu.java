package view.menu.User;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.User.customer.EditPersonalInfoMenuCommand;

public class PersonalInfoMenu extends Menu {
    public PersonalInfoMenu(String name, Menu previousMenu) {
        super(name, previousMenu);
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new EditPersonalInfoMenuCommand(this));
        menuCommands.add(new BackCommand(this));
        menuCommands.add(new HelpCommand(this));
    }
}
