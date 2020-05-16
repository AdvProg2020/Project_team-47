package view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class DeclineRequestCommand extends Command {
    public DeclineRequestCommand(Menu menu) {
        super(menu);
        setSignature("decline [requestId]");
        setRegex("^decline [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController(text);
        getAnswerFromController();
    }

    private void sendMessageToViewToController(String text) {
        String requestId = Arrays.asList(text.split("\\s")).get(1);
        ViewToController.setViewMessage("decline request");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(requestId);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getAnswerFromController() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("successful")) {

        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
