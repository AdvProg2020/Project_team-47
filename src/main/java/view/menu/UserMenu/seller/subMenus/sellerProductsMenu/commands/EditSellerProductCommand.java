package view.menu.UserMenu.seller.subMenus.sellerProductsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;

public class EditSellerProductCommand extends Command {
    public EditSellerProductCommand(Menu menu) {
        super(menu);
        setSignature("edit [productId]");
        setSignature("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        ViewToController.setViewMessage("edit product");
        String productId = Arrays.asList(text.split("\\s")).get(1);
        ArrayList<String> messageInputs = new ArrayList<>();

        messageInputs.add(productId);

        OutputCommands.enterField();
        messageInputs.add(Menu.getInputCommandWithTrim());

        //todo

    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
