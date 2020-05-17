package view.menu.UserMenu.seller.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.Arrays;

public class RemoveProductCommand extends Command {
    public RemoveProductCommand(Menu menu) {
        super(menu);
        setSignature("remove product [productId]");
        setRegex("^remove product [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String productId = Arrays.asList(text.split("\\s")).get(2);
        ViewToController.setViewMessage("remove product seller");
        ViewToController.setFirstString(productId);
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //todo
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
