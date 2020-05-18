package view.menu.productMenu.subMenus.commentsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class AddCommentCommand extends Command {
    public AddCommentCommand(Menu menu) {
        super(menu);
        setSignature("Add comment");
        setRegex("^Add comment$");
    }

    @Override
    public void doCommand(String text) {
        //todo
    }
}
