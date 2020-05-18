package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;

import java.util.ArrayList;

public class ShowAvailableFiltersCommand extends Command {
    public ShowAvailableFiltersCommand(Menu menu) {
        super(menu);
        setSignature("show available filters");
        setRegex("^show available filters$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :
                ViewToController.setViewMessage("show available filters products");
                ViewToController.sendMessageToController();
                break;
            case "offs" :

                 break;
        }
    }

    private void getAnswer() {

        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :
                ServerMessage serverMessage = ViewToController.getServerMessage();
                ArrayList<String> filters = serverMessage.getStrings();
                int index;
                for (String filter : filters) {
                    index = filters.indexOf(filter);
                    System.out.println(index + ". " + filter);
                }
                break;
            case "offs" :

                break;
        }
    }
}
