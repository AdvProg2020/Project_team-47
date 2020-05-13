package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewSalesHistoryCommand extends Command {
    public ViewSalesHistoryCommand(Menu menu) {
        super(menu);
        setSignature("view sales history");
        setRegex("^view sales history$");
    }

    @Override
    public void doCommand(String text) {
    }
}
