package view.command.MenuToMenuCommand;

import view.command.Command;
import view.menu.Menu;

public abstract class MenuToMenuCommand extends Command {
    private Menu nextMenu;
    public MenuToMenuCommand(String signature, Menu menu, Command upperCommand, String regex) {
        super(menu);
    }

}
