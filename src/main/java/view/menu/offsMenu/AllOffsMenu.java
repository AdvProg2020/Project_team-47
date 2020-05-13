package view.menu.offsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.offsMenu.commands.ShowProductInOffCommand;
import view.menu.productMenu.ProductMenu;

public class AllOffsMenu extends Menu {
    public AllOffsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("all offs menu");
    }


    @Override
    protected void setSubMenus() {
        subMenus.add(new ProductMenu(this));
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ShowProductInOffCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }


}
