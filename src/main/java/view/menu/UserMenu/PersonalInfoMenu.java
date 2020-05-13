package view.menu.UserMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.commands.EditPersonalInfoMenuCommand;

public class PersonalInfoMenu extends Menu {
    public PersonalInfoMenu(Menu previousMenu) {
        super(previousMenu);
        setName("personal info menu");
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
