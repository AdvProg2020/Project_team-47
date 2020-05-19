package view.menu.allProductsMenu.subMenus.sortingMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.menu.allProductsMenu.subMenus.sortingMenu.SortingMenu;
import view.outputMessages.OutputCommands;

public class SortAnAvailableSortCommand extends Command {
    public SortAnAvailableSortCommand(Menu menu) {
        super(menu);
        setSignature("sort [an available sort]");
        setRegex("^sort [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        switch (((SortingMenu)this.getMenu()).getType()){
            case "products" :
                ViewToController.setViewMessage("sort an available sort products");
                break;
            case "offs" :
                ViewToController.setViewMessage("sort an available sort offs");
                break;
        }

        getSortFieldAndDirection();
        ViewToController.sendMessageToController();
    }

    private void getSortFieldAndDirection() {
        OutputCommands.enterSortField();
        ViewToController.setFirstString(Menu.getInputCommandWithTrim());

        OutputCommands.enterSortDirection();
        ViewToController.setSecondString(Menu.getInputCommandWithTrim());
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
