package view.menu.mainMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class OffsCommand extends Command {
    public OffsCommand(Menu menu) {
        super(menu);
        setRegex("offs");
    }

    @Override
    public void doCommand(String text) {

    }
}
