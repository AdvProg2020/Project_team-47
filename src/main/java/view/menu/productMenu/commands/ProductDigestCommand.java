package view.menu.productMenu.commands;

import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

public class ProductDigestCommand extends Command {
    public ProductDigestCommand(Menu menu) {
        super(menu);
        setSignature("digest");
        setRegex("^digest$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("digest");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("successful")) {
            showDigest(serverMessage);
            this.getMenu().findSubMenuWithName("digest menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showDigest(ServerMessage serverMessage) {
        // incomplete product price and discount
        ProductInfo productInfo = serverMessage.getProductInfo();
        System.out.println("description : " + productInfo.getDescription());
        System.out.println("main category : " + productInfo.getMainCategory());
        System.out.println("sub category : " + productInfo.getSubCategory());

        System.out.println("sellers name : ");
        for (String sellerName : productInfo.getSellersNames()) {
            System.out.println(sellerName);
        }
        System.out.println("score average : " + productInfo.getScoreAverage());
    }
}
