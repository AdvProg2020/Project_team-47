package view.menu.allProductsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.Arrays;

public class ShowProductCommand extends Command {
    public ShowProductCommand(Menu menu) {
        super(menu);
        setSignature("show product [productId]");
        setRegex("^show product [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        ViewToController.setViewMessage("show product");

        String id = Arrays.asList(text.split("\\s")).get(2);
        ViewToController.setFirstString(id);

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
            this.getMenu().findSubMenuWithName("product menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
