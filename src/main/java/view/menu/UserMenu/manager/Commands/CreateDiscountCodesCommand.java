package view.menu.UserMenu.manager.Commands;

import view.command.Command;
import view.menu.Menu;

public class CreateDiscountCodesCommand extends Command {
    public CreateDiscountCodesCommand(Menu menu) {
        super(menu);
        setSignature("create discount code");
        setRegex("^create discount code$");
    }

    @Override
    public void doCommand(String text) {

    }
}
