package view.menu.productMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.productMenu.commands.ProductAttributesCommand;
import view.menu.productMenu.commands.ProductCommentsCommand;
import view.menu.productMenu.commands.ProductDigestCommand;
import view.menu.productMenu.commands.ProductsCompareCommands;
import view.menu.productMenu.subMenus.commentsMenu.CommentsMenu;
import view.menu.productMenu.subMenus.digestMenu.DigestMenu;

public class ProductMenu extends Menu {
    public ProductMenu(Menu previousMenu) {
        super(previousMenu);
        setName("product menu");
    }


    @Override
    protected void setSubMenus() {
        subMenus.add(new DigestMenu(this));
        subMenus.add(new CommentsMenu(this));
    }

    @Override
    protected void addCommands() {
        menuCommands.add(new ProductDigestCommand(this));
        menuCommands.add(new ProductAttributesCommand(this));
        menuCommands.add(new ProductsCompareCommands(this));
        menuCommands.add(new ProductCommentsCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
