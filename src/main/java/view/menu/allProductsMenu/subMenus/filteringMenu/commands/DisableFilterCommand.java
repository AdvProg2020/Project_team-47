package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class DisableFilterCommand extends Command {
    public DisableFilterCommand(Menu menu) {
        super(menu);
        setSignature("disable filter [a selected filter]");
        setRegex("^disable filter [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
