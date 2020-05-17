package view.menu.productMenu.subMenus.commentsMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.productMenu.subMenus.commentsMenu.commands.AddCommentCommand;

public class CommentsMenu extends Menu {
    public CommentsMenu(Menu previousMenu) {
        super(previousMenu);
        setName("comment menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new AddCommentCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
