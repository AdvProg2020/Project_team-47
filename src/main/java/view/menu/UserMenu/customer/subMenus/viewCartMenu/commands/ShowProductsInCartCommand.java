package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import model.send.receive.CartInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ShowProductsInCartCommand extends Command {
    public ShowProductsInCartCommand(Menu menu) {
        super(menu);
        setSignature("show products");
        setRegex("^show products$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("show products in cart");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("successful")) {
            CartInfo cartInfo = serverMessage.getCartInfo();
            showCartInfo(cartInfo);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showCartInfo(CartInfo cartInfo) {
        //todo
    }

}
