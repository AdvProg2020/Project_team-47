package view.menu.productMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class ProductAttributesCommand extends Command {
    public ProductAttributesCommand(Menu menu) {
        super(menu);
        setSignature("attributes");
        setRegex("^attributes$");
    }

    @Override
    public void doCommand(String text) {

    }
}
