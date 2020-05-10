package view.command.ViewToControllerCommand;

import view.command.Command;
import view.menu.Menu;

public abstract class ViewToControllerCommand extends Command {

    public ViewToControllerCommand(String signature, Menu menu, Command upperCommand, String regex) {
        super(menu);
    }


}
