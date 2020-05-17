package view.menu.UserMenu.seller.subMenus.viewOffsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands.AddOffCommandSeller;
import view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands.EditOffCommandSeller;
import view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands.ViewOffCommandSeller;

public class ViewOffsMenu extends Menu {
    public ViewOffsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("view offs menu");
    }

    @Override
    protected void setSubMenus() {
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewOffCommandSeller(this));
        menuCommands.add(new EditOffCommandSeller(this));
        menuCommands.add(new AddOffCommandSeller(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
