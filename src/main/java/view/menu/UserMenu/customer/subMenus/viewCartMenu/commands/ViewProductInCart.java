package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class ViewProductInCart extends Command {
    public ViewProductInCart(Menu menu) {
        super(menu);
        setSignature("view [productId]");
        setRegex("^view [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(1);
        ViewToController.setViewMessage("view product in cart");
        ViewToController.setFirstString(productId);

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //todo
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }


}
