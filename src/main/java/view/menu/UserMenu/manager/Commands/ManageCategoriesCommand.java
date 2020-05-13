package view.menu.UserMenu.manager.Commands;

import view.command.Command;
import view.menu.Menu;

public class ManageCategoriesCommand extends Command {
    public ManageCategoriesCommand(Menu menu) {
        super(menu);
        setSignature("manage categories");
        setRegex("^manage categories$");
    }

    @Override
    public void doCommand(String text) {

    }
}
