package view.menu.UserMenu.manager.subMenus.manageRequestsMenu.commands;

import model.others.request.Request;
import model.send.receive.RequestInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class RequestDetailsCommand extends Command {
    public RequestDetailsCommand(Menu menu) {
        super(menu);
        setSignature("details [requestId]");
        setRegex("^details [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController(text);
        getAnswerFromController();
    }

    private void sendMessageToViewToController(String text) {
        String requestId = Arrays.asList(text.split("\\s")).get(1);
        ViewToController.setViewMessage("request details");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(requestId);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void getAnswerFromController() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            printRequestInfo(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void printRequestInfo(ServerMessage serverMessage) {
        RequestInfo requestInfo = serverMessage.getRequestInfo();

        System.out.println("id : " + requestInfo.getId());
        System.out.println("requestedSenderUsername : " + requestInfo.getRequestedSenderUsername());
        System.out.println("type : " + requestInfo.getType());
    }
}
