package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class DisableSortCommand extends Command {
    public DisableSortCommand(Menu menu) {
        super(menu);
        setSignature("disable sort");
        setRegex("^disable sort$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
