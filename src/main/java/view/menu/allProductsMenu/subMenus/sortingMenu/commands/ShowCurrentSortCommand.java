package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;

public class ShowCurrentSortCommand extends Command {
    public ShowCurrentSortCommand(Menu menu) {
        super(menu);
        setSignature("current sort");
        setRegex("^current sort$");
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
