package view.menu.productMenu.subMenus.digestMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class SelectProductSellerCommand extends Command {
    public SelectProductSellerCommand(Menu menu) {
        super(menu);
        setSignature("select seller [seller_username]");
        setRegex("^select seller [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {

    }
}
