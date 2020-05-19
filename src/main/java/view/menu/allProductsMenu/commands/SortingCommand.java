package view.menu.allProductsMenu.commands;

import view.command.Command;
import view.menu.Menu;

public class SortingCommand extends Command {
    private String type; // products or offs

    public SortingCommand(Menu menu, String type) {
        super(menu);
        setSignature("sorting");
        setRegex("^sorting$");
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
        this.getMenu().findSubMenuWithName("sorting menu").autoExecute();
    }
}
