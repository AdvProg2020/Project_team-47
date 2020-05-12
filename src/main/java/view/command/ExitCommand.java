package view.command;

import view.menu.Menu;

public class ExitCommand extends Command {
    public ExitCommand(Menu menu) {
        super(menu);
        setSignature("exit");
        setRegex("exit");
    }

    @Override
    public void doCommand(String text) {
        System.exit(5);
    }
}
