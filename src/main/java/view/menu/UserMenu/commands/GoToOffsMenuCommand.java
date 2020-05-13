package view.menu.UserMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class GoToOffsMenuCommand extends Command {
    public GoToOffsMenuCommand(Menu menu) {
        super(menu);
        setSignature("offs");
        setRegex("^offs$");
    }

    @Override
    public void doCommand(String text) {
        this.menu.findSubMenuWithName("all offs menu").autoExecute();
    }
}
