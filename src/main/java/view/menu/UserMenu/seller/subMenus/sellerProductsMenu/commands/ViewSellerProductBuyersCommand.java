package view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands;

import model.send.receive.ServerMessage;
import model.send.receive.UserInfo;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewSellerProductBuyersCommand extends Command {
    public ViewSellerProductBuyersCommand(Menu menu) {
        super(menu);
        setSignature("view buyers [productId]");
        setSignature("^view buyers [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        ViewToController.setViewMessage("view product buyers");
        String productId = Arrays.asList(text.split("\\s")).get(2);
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(productId);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            showBuyers(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showBuyers(ServerMessage serverMessage) {
        ArrayList<String> buyersUsername = new ArrayList<>();
        for (UserInfo userInfo : serverMessage.getUserInfoArrayList()) {
            buyersUsername.add(userInfo.getUsername());
        }
        for (String username : buyersUsername) {
            System.out.println(username);
        }
    }
}
