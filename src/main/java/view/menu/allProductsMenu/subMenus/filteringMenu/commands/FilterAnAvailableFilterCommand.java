package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class FilterAnAvailableFilterCommand extends Command {
    public FilterAnAvailableFilterCommand(Menu menu) {
        super(menu);
        setSignature("filter [an available filter]");
        setRegex("^filter [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
