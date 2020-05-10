package view.command;

import view.Menu;

public class MenuToControllerCommand extends Command {

    public MenuToControllerCommand(String signature, Menu menu, String regex) {
        super(signature, menu, regex);
    }

    @Override
    public void doCommand() {

    }
}
