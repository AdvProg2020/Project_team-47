package view.command;

import view.Menu;

public class MenuToMenuCommand extends Command {
    private Menu nextMenu;
    public MenuToMenuCommand(String signature, Menu menu, String regex) {
        super(signature, menu, regex);
    }

    @Override
    public void doCommand() {
        nextMenu.execute();
    }
}
