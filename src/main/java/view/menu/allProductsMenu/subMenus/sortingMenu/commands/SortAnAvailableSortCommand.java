package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class SortAnAvailableSortCommand extends Command {
    public SortAnAvailableSortCommand(Menu menu) {
        super(menu);
        setSignature("sort [an available sort]");
        setRegex("^sort [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
