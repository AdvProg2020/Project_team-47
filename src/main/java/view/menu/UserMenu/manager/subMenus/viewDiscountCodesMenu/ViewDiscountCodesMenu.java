package view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands.EditDiscountCodeCommand;
import view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands.RemoveDiscountCodeCommand;
import view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands.ViewDiscountCodeCommand;

public class ViewDiscountCodesMenu extends Menu {
    public ViewDiscountCodesMenu(Menu previousMenu) {
        super(previousMenu);
        setName("view discount codes menu");
    }

    @Override
    protected void setSubMenus() {
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ViewDiscountCodeCommand(this));
        menuCommands.add(new EditDiscountCodeCommand(this));
        menuCommands.add(new RemoveDiscountCodeCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
