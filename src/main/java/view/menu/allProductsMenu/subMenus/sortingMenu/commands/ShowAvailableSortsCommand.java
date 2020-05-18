package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowAvailableSortsCommand extends Command {
    public ShowAvailableSortsCommand(Menu menu) {
        super(menu);
        setSignature("show available sorts");
        setRegex("^show available sorts$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
