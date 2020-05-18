package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class FilteringCommand extends Command {
    public FilteringCommand(Menu menu) {
        super(menu);
        setSignature("filtering");
        setRegex("^filtering$");
    }

    @Override
    public void doCommand(String text) {
        this.getMenu().findSubMenuWithName("filtering menu").autoExecute();
    }
}
