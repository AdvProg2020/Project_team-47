package view.menu.UserMenu.manager.subMenus.manageCategoriesMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class RemoveCategoryCommand extends Command {
    public RemoveCategoryCommand(Menu menu) {
        super(menu);
        setSignature("remove [category]");
        setRegex("^remove [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
