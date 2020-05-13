package view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewDiscountCodeCommand extends Command {
    public ViewDiscountCodeCommand(Menu menu) {
        super(menu);
        setSignature("view discount code [code]");
        setRegex("^view discount code [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
