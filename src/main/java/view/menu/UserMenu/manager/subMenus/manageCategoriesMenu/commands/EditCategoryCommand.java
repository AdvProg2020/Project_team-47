package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class EditCategoryCommand extends Command {
    public EditCategoryCommand(Menu menu) {
        super(menu);
        setSignature("edit [category]");
        setRegex("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
