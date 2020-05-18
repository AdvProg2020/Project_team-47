package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;

public class FilterAnAvailableFilterCommand extends Command {
    public FilterAnAvailableFilterCommand(Menu menu) {
        super(menu);
        setSignature("filter [an available filter]");
        setRegex("^filter [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :

                break;
            case "offs" :

                break;
        }
    }

    private void getAnswer() {
        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :

                break;
            case "offs" :

                break;
        }
    }
}
