package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands.*;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu previousMenu) {
        super(previousMenu);
        setName("manage categories menu");
    }


    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new EditMainCategoryCommand(this));
        menuCommands.add(new EditSubCategoryCommand(this));
        menuCommands.add(new AddMainCategoryCommand(this));
        menuCommands.add(new AddSubCategoryCommand(this));
        menuCommands.add(new RemoveMainCategoryCommand(this));
        menuCommands.add(new RemoveSubCategoryCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
