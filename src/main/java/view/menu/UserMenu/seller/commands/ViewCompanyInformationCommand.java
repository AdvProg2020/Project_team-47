package view.menu.UserMenu.seller.commands;

import view.command.Command;
import view.menu.Menu;

public class ViewCompanyInformationCommand extends Command {
    public ViewCompanyInformationCommand(Menu menu) {
        super(menu);
        setSignature("view company information");
        setRegex("^view company information$");
    }

    @Override
    public void doCommand(String text) {

    }
}
