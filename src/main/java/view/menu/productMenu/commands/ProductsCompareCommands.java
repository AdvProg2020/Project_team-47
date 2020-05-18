package view.menu.productMenu.commands;

import model.others.Product;
import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.Arrays;

public class ProductsCompareCommands extends Command {
    public ProductsCompareCommands(Menu menu) {
        super(menu);
        setSignature("compare [productID]");
        setRegex("^compare [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String otherProductId = Arrays.asList(text.split("\\s")).get(1);

        ViewToController.setViewMessage("compare products");
        ViewToController.setFirstString(otherProductId);

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            compare(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void compare(ServerMessage serverMessage) {
        //incomplete
        ProductInfo firstProductInfo = serverMessage.getProductInfoArrayList().get(0);
        ProductInfo secondProductInfo = serverMessage.getProductInfoArrayList().get(1);

        System.out.println("name : " + "1. " + firstProductInfo.getName() + "2. " + secondProductInfo.getName());
        System.out.println("id : " + "1. " + firstProductInfo.getId() + "2. " + secondProductInfo.getId());
        System.out.println("scoreAverage : " + "1. " +  firstProductInfo.getScoreAverage() + "2. " + secondProductInfo.getScoreAverage());
        System.out.println("description : " + "1. " +  firstProductInfo.getDescription() + "2. " + secondProductInfo.getDescription());
        System.out.println("status : " + "1. " +  firstProductInfo.getStatus() + "2. " + secondProductInfo.getStatus());
        System.out.println("seenTime : " + "1. " +  firstProductInfo.getSeenTime() + "2. " + secondProductInfo.getSeenTime());
        System.out.println("mainCategory : " + "1. " +  firstProductInfo.getMainCategory() + "2. " + secondProductInfo.getMainCategory());
        System.out.println("subCategory : " + "1. " +  firstProductInfo.getSubCategory() + "2. " + secondProductInfo.getSubCategory());
    }
}
