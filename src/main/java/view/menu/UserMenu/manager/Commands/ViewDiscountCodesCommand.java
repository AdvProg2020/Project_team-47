package view.menu.UserMenu.manager.Commands;

import view.command.Command;
import view.menu.Menu;

public class ViewDiscountCodesCommand extends Command {
    public ViewDiscountCodesCommand(Menu menu) {
        super(menu);
        setSignature("view discount codes");
        setRegex("^view discount codes$");
    }

    @Override
    public void doCommand(String text) {
        this.getMenu().findSubMenuWithName("view discount codes menu").autoExecute();
    }
}
