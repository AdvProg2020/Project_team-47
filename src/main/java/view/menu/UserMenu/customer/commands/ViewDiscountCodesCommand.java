package view.menu.UserMenu.customer.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewDiscountCodesCommand extends Command {
    public ViewDiscountCodesCommand(Menu menu) {
        super(menu);
        setSignature("view discount codes");
        setRegex("^view discount codes$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view discount codes customer");

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("successful")) {
            showDiscountCodes(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showDiscountCodes(ServerMessage serverMessage) {
        //todo
    }

}
