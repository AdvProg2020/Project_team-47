package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;

import java.util.ArrayList;

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
                ViewToController.setViewMessage("show current sort products");
                break;
            case "offs" :
                ViewToController.setViewMessage("show current sort offs");
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
        String sortField = serverMessage.getFirstString();
        String sortDirection = serverMessage.getSecondString();

        System.out.println("sort field : " + sortField);
        System.out.println("sort direction : " + sortDirection);
    }

}
