package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;

import java.util.ArrayList;

public class ShowCurrentFiltersCommand extends Command {
    public ShowCurrentFiltersCommand(Menu menu) {
        super(menu);
        setSignature("current filters");
        setRegex("^current filters$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {

        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :
                ViewToController.setViewMessage("current filters products");
                break;
            case "offs" :
                ViewToController.setViewMessage("current filters offs");
                break;
        }
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {

        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            ArrayList<String> currentFilters = serverMessage.getStrings();
            showFiltersProducts(currentFilters);
        } else {
            System.out.println(serverMessage.getFirstString());
        }


    }

    private void showFiltersProducts(ArrayList<String> currentFilters) {
        int index;
        for (String currentFilter : currentFilters) {
            index = currentFilters.indexOf(currentFilter) + 1;
            System.out.println(index + ". " + currentFilter);
        }
    }
}
