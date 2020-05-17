package view.menu.UserMenu.customer.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewCartCommand extends Command {
    public ViewCartCommand(Menu menu) {
        super(menu);
        setSignature("view cart");
        setRegex("^view cart$");
    }

    @Override
    public void doCommand(String text) {
        this.getMenu().findSubMenuWithName("view cart menu").autoExecute();
    }
}
