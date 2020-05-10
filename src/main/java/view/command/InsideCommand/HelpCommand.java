package view.command.InsideCommand;

import view.command.Command;
import view.menu.Menu;

public class HelpCommand extends InsideCommand{
    public HelpCommand(Menu menu) {
        super(menu);
    }

    public HelpCommand(String signature, Menu menu, Command upperCommand) {
        super(menu);
    }

    @Override
    public void doCommand() {
        for (Command command : menu.getMenuCommands()) {
            System.out.println(menu.getMenuCommands().indexOf(command) + ". " + command.getSignature());
        }
    }
}
