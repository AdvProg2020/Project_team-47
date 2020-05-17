package view.menu.UserMenu.customer.subMenus.ordersMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;

import java.util.ArrayList;
import java.util.Arrays;

public class RateOrderCommand extends Command {
    public RateOrderCommand(Menu menu) {
        super(menu);
        setSignature("rate [productId] [1-5]");
        setRegex("^rate [^\\s]+ [12345]$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String orderId = Arrays.asList(text.split("\\s")).get(1);
        String score = Arrays.asList(text.split("\\s")).get(2);

        ViewToController.setViewMessage("rate product");
        ViewToController.setFirstString(orderId);
        ViewToController.setSecondString(score);

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
