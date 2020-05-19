package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;

public class DisableSortCommand extends Command {
    public DisableSortCommand(Menu menu) {
        super(menu);
        setSignature("disable sort");
        setRegex("^disable sort$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        switch (((SortingMenu) this.getMenu()).getType()) {
            case "products":
                ViewToController.setViewMessage("disable sort products");
                break;
            case "offs":
                ViewToController.setViewMessage("disable sort offs");
                break;
        }
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
