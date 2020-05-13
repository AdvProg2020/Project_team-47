package view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class EditDiscountCodeCommand extends Command {
    public EditDiscountCodeCommand(Menu menu) {
        super(menu);
        setSignature("edit discount code [code]");
        setRegex("^edit discount code [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
