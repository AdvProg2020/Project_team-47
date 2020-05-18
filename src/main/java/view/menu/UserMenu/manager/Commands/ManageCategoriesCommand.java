package view.menu.UserMenu.manager.Commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;

public class ManageCategoriesCommand extends Command {
    public ManageCategoriesCommand(Menu menu) {
        super(menu);
        setSignature("manage categories");
        setRegex("^manage categories$");
    }

    @Override
    public void doCommand(String text) {
        getSortFieldAndDirection();
        getAnswer();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            showCategories();
            this.getMenu().findSubMenuWithName("manage categories menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showCategories() {
        //todo
    }

    private void getSortFieldAndDirection() {
        ArrayList<String> messageInputs = new ArrayList<>();
        OutputCommands.enterSortField();
        messageInputs.add(Menu.getInputCommandWithTrim());
        OutputCommands.enterSortDirection();
        messageInputs.add(Menu.getInputCommandWithTrim());
        sendRequest(messageInputs);
    }

    private void sendRequest(ArrayList<String> messageInputs) {
        ViewToController.setViewMessage("manage categories");
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

}
