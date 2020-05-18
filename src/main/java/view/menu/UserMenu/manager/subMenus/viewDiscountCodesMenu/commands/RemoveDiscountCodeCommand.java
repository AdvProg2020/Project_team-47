package view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveDiscountCodeCommand extends Command {
    public RemoveDiscountCodeCommand(Menu menu) {
        super(menu);
        setSignature("remove discount code [code]");
        setRegex("^remove discount code [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String code = Arrays.asList(text.split("\\s")).get(3);
        sendMessageToViewToController(code);
        getControllerAnswer();
    }

    private void sendMessageToViewToController(String code) {
        ViewToController.setViewMessage("remove discount code manager");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(code);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getControllerAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
