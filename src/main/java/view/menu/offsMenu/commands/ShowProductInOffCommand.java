package view.menu.offsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowProductInOffCommand extends Command {
    public ShowProductInOffCommand(Menu menu) {
        super(menu);
        setSignature("show product [productId]");
        setRegex("^show product [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController(text);
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            this.getMenu().findSubMenuWithName("product menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void sendMessageToViewToController(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(2);
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(productId);
        ViewToController.setViewMessage("go to product page");
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }
}
