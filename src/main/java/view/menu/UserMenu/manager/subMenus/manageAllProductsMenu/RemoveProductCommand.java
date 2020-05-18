package view.menu.UserMenu.manager.subMenus.manageAllProductsMenu;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveProductCommand extends Command {
    public RemoveProductCommand(Menu menu) {
        super(menu);
        setSignature("remove [productId]");
        this.setRegex("^remove [^\\s]+$");

    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController(text);
        getMessageFromViewToController();
    }

    private void getMessageFromViewToController() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    void sendMessageToViewToController(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(1);
        ViewToController.setViewMessage("remove product manager");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(productId);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

}
