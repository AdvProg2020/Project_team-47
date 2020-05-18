package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class FilteringCommand extends Command {
    private String type; // products or offs
    public FilteringCommand(Menu menu, String type) {
        super(menu);
        setSignature("filtering");
        setRegex("^filtering$");
        setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void doCommand(String text) {
        this.getMenu().findSubMenuWithName("filtering menu").autoExecute();
    }
}
