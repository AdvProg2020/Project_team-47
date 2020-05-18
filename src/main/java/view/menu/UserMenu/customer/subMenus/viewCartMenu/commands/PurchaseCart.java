package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.UserMenu.customer.subMenus.viewCartMenu.subMenus.ReceiverInformationMenu;
import view.outputMessages.OutputComments;

public class PurchaseCart extends Command {
    public PurchaseCart(Menu menu) {
        super(menu);
        setSignature("purchase");
        setRegex("^purchase");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("purchase cart");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            ((ReceiverInformationMenu) (this.menu.findSubMenuWithName("receiver information menu"))).manualExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
            if (serverMessage.getFirstString().equals("you should log in first")) {
                OutputComments.goingToLoginMenu();
                this.getMenu().findSubMenuWithName("login/register menu").autoExecute();
            } else {
                //unsure
            }
        }
    }
}
