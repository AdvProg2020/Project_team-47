package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;

import java.util.ArrayList;

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
                ViewToController.setViewMessage("show available sorts products");
                break;
            case "offs" :
                ViewToController.setViewMessage("show available sorts offs");
                break;
        }
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            showSorts(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showSorts(ServerMessage serverMessage) {
        ArrayList<String> sorts = serverMessage.getStrings();

        int index;
        for (String sort : sorts) {
            index = sorts.indexOf(sort);
            System.out.println(index + ". " + sort);
        }
    }
}
