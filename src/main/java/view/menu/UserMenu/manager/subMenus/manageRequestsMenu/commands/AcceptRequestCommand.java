package view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class AcceptRequestCommand extends Command {
    public AcceptRequestCommand(Menu menu) {
        super(menu);
        setSignature("accept [requestId]");
        setRegex("^accept [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController(text);
        getAnswerFromController();
    }

    private void sendMessageToViewToController(String text) {
        String requestId = Arrays.asList(text.split("\\s")).get(1);
        ViewToController.setViewMessage("accept request");
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
