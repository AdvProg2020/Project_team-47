package view.menu.UserMenu.manager.subMenus.viewDiscountCodesMenu.commands;

import model.send.receive.DiscountCodeInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class EditDiscountCodeCommand extends Command {
    public EditDiscountCodeCommand(Menu menu) {
        super(menu);
        setSignature("edit discount code [code]");
        setRegex("^edit discount code [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        String code = Arrays.asList(text.split("\\s")).get(3);
        sendMessageToViewToController(code);
        getControllerAnswer();
    }

    private void sendMessageToViewToController(String code) {
        ViewToController.setViewMessage("edit discount code manager");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(code);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getControllerAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            DiscountCodeInfo discountCodeInfo = serverMessage.getDiscountCodeInfo();
            //todo
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
