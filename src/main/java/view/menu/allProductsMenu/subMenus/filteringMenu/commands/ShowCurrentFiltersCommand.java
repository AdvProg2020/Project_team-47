package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowCurrentFiltersCommand extends Command {
    public ShowCurrentFiltersCommand(Menu menu) {
        super(menu);
        setSignature("current filters");
        setRegex("^current filters$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
