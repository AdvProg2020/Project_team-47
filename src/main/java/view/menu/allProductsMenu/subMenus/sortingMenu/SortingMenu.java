package view.menu.allProductsMenu.subMenus.sortingMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.sortingMenu.commands.DisableSortCommand;
import view.menu.allProductsMenu.subMenus.sortingMenu.commands.ShowAvailableSortsCommand;
import view.menu.allProductsMenu.subMenus.sortingMenu.commands.ShowCurrentSortCommand;
import view.menu.allProductsMenu.subMenus.sortingMenu.commands.SortAnAvailableSortCommand;

public class SortingMenu extends Menu {
    public SortingMenu(Menu previousMenu) {
        super(previousMenu);
        setName("sorting menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new SortAnAvailableSortCommand(this));
        menuCommands.add(new ShowAvailableSortsCommand(this));
        menuCommands.add(new ShowCurrentSortCommand(this));
        menuCommands.add(new DisableSortCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
