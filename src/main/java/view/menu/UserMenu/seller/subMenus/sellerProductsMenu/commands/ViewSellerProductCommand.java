package view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewSellerProductCommand extends Command {
    public ViewSellerProductCommand(Menu menu) {
        super(menu);
        setSignature("view [productId]");
        setSignature("^view [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        ViewToController.setViewMessage("view product");
        String productId = Arrays.asList(text.split("\\s")).get(1);
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(productId);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
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
