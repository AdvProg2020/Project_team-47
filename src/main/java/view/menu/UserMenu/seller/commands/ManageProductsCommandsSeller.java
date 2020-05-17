package view.menu.UserMenu.seller.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

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
            showSellerProducts();
            this.getMenu().findSubMenuWithName("seller products menu").autoExecute();
        } else{
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showSellerProducts() {
        //todo
    }

}
