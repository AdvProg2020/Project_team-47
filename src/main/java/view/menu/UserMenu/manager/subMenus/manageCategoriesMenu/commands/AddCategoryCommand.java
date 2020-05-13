package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class AddCategoryCommand extends Command {
    public AddCategoryCommand(Menu menu) {
        super(menu);
        setSignature("add [category]");
        setRegex("^add [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
