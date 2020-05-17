package view.menu.productMenu.subMenus.digestMenu;

import view.command.BackCommand;
import view.command.HelpCommand;
import view.menu.Menu;
import view.menu.productMenu.subMenus.digestMenu.commands.AddProductToCartCommand;
import view.menu.productMenu.subMenus.digestMenu.commands.SelectProductSellerCommand;

public class DigestMenu extends Menu {
    public DigestMenu(Menu previousMenu) {
        super(previousMenu);
        setName("digest menu");
    }

    @Override
    protected void setSubMenus() {

    }

    @Override
    protected void addCommands() {
        menuCommands.add(new AddProductToCartCommand(this));
        menuCommands.add(new SelectProductSellerCommand(this));
        menuCommands.add(new HelpCommand(this));
        menuCommands.add(new BackCommand(this));
    }
}
