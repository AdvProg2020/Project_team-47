package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowAvailableFiltersCommand extends Command {
    public ShowAvailableFiltersCommand(Menu menu) {
        super(menu);
        setSignature("show available filters");
        setRegex("^show available filters$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
