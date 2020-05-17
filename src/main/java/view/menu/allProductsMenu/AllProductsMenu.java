package view.menu.allProductsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.allProductsMenu.commands.*;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;
import view.menu.productMenu.ProductMenu;

public class AllProductsMenu extends Menu {
    public AllProductsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("all products menu");
    }



    @Override
    protected void setSubMenus() {
        subMenus.add(new FilteringMenu(this));
        subMenus.add(new SortingMenu(this));
        subMenus.add(new ProductMenu(this));
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewCategoriesCommand(this));
        menuCommands.add(new FilteringCommand(this));
        menuCommands.add(new SortingCommand(this));
        menuCommands.add(new ShowProductsCommand(this));
        menuCommands.add(new ShowProductCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
