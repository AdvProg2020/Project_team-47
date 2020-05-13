package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewCategoriesCommand extends Command {
    public ViewCategoriesCommand(Menu menu) {
        super(menu);
        setSignature("view categories");
        setRegex("^view categories$");
    }

    @Override
    public void doCommand(String text) {

    }
}
