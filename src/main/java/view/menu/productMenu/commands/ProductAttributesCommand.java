package view.menu.productMenu.commands;

import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

public class ProductAttributesCommand extends Command {
    public ProductAttributesCommand(Menu menu) {
        super(menu);
        setSignature("attributes");
        setRegex("^attributes$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("product attributes");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            showProductAttributes(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showProductAttributes(ServerMessage serverMessage) {
        // incomplete
        ProductInfo productInfo = serverMessage.getProductInfo();
        System.out.println("name : " + productInfo.getName());
        System.out.println("id : " + productInfo.getId());
        System.out.println("scoreAverage : " + productInfo.getScoreAverage());
        System.out.println("description : " + productInfo.getDescription());
        System.out.println("status : " + productInfo.getStatus());
        System.out.println("seenTime : " + productInfo.getSeenTime());
        System.out.println("mainCategory : " + productInfo.getMainCategory());
        System.out.println("subCategory : " + productInfo.getSubCategory());

        System.out.println("sellers name : ");
        for (String sellerName : productInfo.getSellersNames()) {
            System.out.println(sellerName);
        }

        System.out.println("special properties : ");
        productInfo.getSpecialProperties().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }
}
