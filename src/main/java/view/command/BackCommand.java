package view.command;

import view.menu.Menu;

public class BackCommand extends Command {
    public BackCommand(Menu menu) {
        super(menu);
        setSignature("back");
        setRegex("back");
    }

    @Override
    public void doCommand(String text) {
        menu.getPreviousMenu().autoExecute();
    }
}
