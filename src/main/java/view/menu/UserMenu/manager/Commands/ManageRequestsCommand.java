package view.menu.UserMenu.manager.Commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

import java.util.ArrayList;

public class ManageRequestsCommand extends Command {
    public ManageRequestsCommand(Menu menu) {
        super(menu);
        setSignature("manage requests");
        setRegex("^manage requests$");
    }

    @Override
    public void doCommand(String text) {
        getSortAndDirection();
        sendMessageToViewToController();
        getControllerAnswer();
    }

    private void getSortAndDirection() {
        ArrayList<String> messageInputs = new ArrayList<>();
        OutputQuestions.sortList();
        String answer = Menu.getInputCommandWithTrim();
        if (answer.equalsIgnoreCase("no")) {
            ViewToController.setViewMessageArrayListInputs(messageInputs);
        } else if (answer.equalsIgnoreCase("yes")) {
            OutputCommands.enterSortField();
            String sortField = Menu.getInputCommandWithTrim();
            OutputCommands.enterSortDirection();
            String direction = Menu.getInputCommandWithTrim();
            messageInputs.add(sortField);
            messageInputs.add(direction);
            ViewToController.setViewMessageArrayListInputs(messageInputs);
        } else {
            OutputErrors.invalidAnswer();
            getSortAndDirection();
        }
    }

    private void sendMessageToViewToController() {
        ViewToController.setViewMessage("manage requests");
        ViewToController.sendMessageToController();
    }

    private void getControllerAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            goToRequestsMenu();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void goToRequestsMenu() {
        this.getMenu().findSubMenuWithName("manage requests menu").autoExecute();
    }

}
