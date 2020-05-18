package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class SortingCommand extends Command {
    public SortingCommand(Menu menu) {
        super(menu);
        setSignature("sorting");
        setRegex("^sorting$");
    }

    @Override
    public void doCommand(String text) {
        this.getMenu().findSubMenuWithName("sorting menu").autoExecute();
    }
}
