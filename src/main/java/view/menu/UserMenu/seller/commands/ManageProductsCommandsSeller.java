package view.menu.UserMenu.seller.commands;

import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ManageProductsCommandsSeller extends Command {
    public ManageProductsCommandsSeller(Menu menu) {
        super(menu);
        setSignature("manage products");
        setRegex("^manage products$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("manage products seller");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            showSellerProducts(serverMessage);
            this.getMenu().findSubMenuWithName("seller products menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showSellerProducts(ServerMessage serverMessage) {
        ArrayList<ProductInfo> productInfoArrayList = serverMessage.getProductInfoArrayList();
        for (ProductInfo productInfo : productInfoArrayList) {
            System.out.println("name : " + productInfo.getName() + " id : " + productInfo.getId());
        }
    }

}
