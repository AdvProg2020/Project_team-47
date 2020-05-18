package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.Arrays;

public class DecreaseProductInCart extends Command {
    public DecreaseProductInCart(Menu menu) {
        super(menu);
        setSignature("decrease [productId]");
        setRegex("^decrease [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(1);
        ViewToController.setViewMessage("decrease product in cart");

        ViewToController.setFirstString(productId);
        getProductSeller();
    }

    private void getProductSeller() {
        OutputCommands.enterSellerUsername();
        ViewToController.setSecondString(Menu.getInputCommandWithTrim());
    }


    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getSecondString());
        }
    }

}
