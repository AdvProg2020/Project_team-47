package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowCurrentSortCommand extends Command {
    public ShowCurrentSortCommand(Menu menu) {
        super(menu);
        setSignature("current sort");
        setRegex("^current sort$");
    }

    @Override
    public void doCommand(String text) {

    }
}
