package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;

public class ShowAvailableSortsCommand extends Command {
    public ShowAvailableSortsCommand(Menu menu) {
        super(menu);
        setSignature("show available sorts");
        setRegex("^show available sorts$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        switch (((SortingMenu)this.getMenu()).getType()){
            case "products" :

                break;
            case "offs" :

                break;
        }
    }

    private void getAnswer() {
        switch (((SortingMenu)this.getMenu()).getType()){
            case "products" :

                break;
            case "offs" :

                break;
        }
    }
}
