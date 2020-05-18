package view.menu.allProductsMenu.subMenus.filteringMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.allProductsMenu.subMenus.filteringMenu.FilteringMenu;
import view.outputMessages.OutputCommands;

public class DisableFilterCommand extends Command {
    public DisableFilterCommand(Menu menu) {
        super(menu);
        setSignature("disable filter [a selected filter]");
        setRegex("^disable filter [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {

        switch (((FilteringMenu)this.getMenu()).getType()){
            case "products" :
                ViewToController.setViewMessage("disable a selected filter products");
                break;
            case "offs" :
                ViewToController.setViewMessage("disable a selected filter offs");
                break;
        }

        getFilterKey();
        ViewToController.sendMessageToController();
    }

    private void getFilterKey() {
        OutputCommands.enterFilterKey();
        ViewToController.setFirstString(Menu.getInputCommandWithTrim());
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
