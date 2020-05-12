package view.command;

import view.menu.Menu;

public class HelpCommand extends Command {
    public HelpCommand(Menu menu) {
        super(menu);
        setSignature("help");
        setRegex("^help$");
    }

    @Override
    public void doCommand(String text) {
        for (Command command : menu.getMenuCommands()) {
            System.out.println(menu.getMenuCommands().indexOf(command) + 1 + ". " + command.getSignature());
        }
    }
}
