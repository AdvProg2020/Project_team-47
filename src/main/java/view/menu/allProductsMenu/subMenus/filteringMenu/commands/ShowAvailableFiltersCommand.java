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
        switch (((FilteringMenu) this.getMenu()).getType()) {
            case "products":
                ViewToController.setViewMessage("show available filters products");
                break;
            case "offs":
                ViewToController.setViewMessage("show available filters offs");
                break;
        }
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            ArrayList<String> filters = serverMessage.getStrings();

            int index;
            for (String filter : filters) {
                index = filters.indexOf(filter);
                System.out.println(index + ". " + filter);
            }

        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
