package view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class RemoveDiscountCodeCommand extends Command {
    public RemoveDiscountCodeCommand(Menu menu) {
        super(menu);
        setSignature("remove discount code [code]");
        setRegex("^remove discount code [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
