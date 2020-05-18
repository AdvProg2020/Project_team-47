package view.menu.allProductsMenu.subMenus.filteringMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.commands.DisableFilterCommand;
import view.menu.allProductsMenu.subMenus.filteringMenu.commands.FilterAnAvailableFilterCommand;
import view.menu.allProductsMenu.subMenus.filteringMenu.commands.ShowAvailableFiltersCommand;
import view.menu.allProductsMenu.subMenus.filteringMenu.commands.ShowCurrentFiltersCommand;

public class FilteringMenu extends Menu {
    private String type;// products or offs
    public FilteringMenu(Menu previousMenu, String type) {
        super(previousMenu);
        this.setName("filtering menu");
        this.setType("type");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ShowAvailableFiltersCommand(this));
        menuCommands.add(new FilterAnAvailableFilterCommand(this));
        menuCommands.add(new ShowCurrentFiltersCommand(this));
        menuCommands.add(new DisableFilterCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
