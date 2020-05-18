package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class ShowCategoriesCommand extends Command {
    public ShowCategoriesCommand(Menu menu) {
        super(menu);
        setSignature("show categories");
        setRegex("^show categories$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        //todo
    }

    private void getAnswer() {
        //todo
    }
}
