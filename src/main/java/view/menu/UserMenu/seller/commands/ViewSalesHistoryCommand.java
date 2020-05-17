package view.menu.UserMenu.seller.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.lang.management.MemoryUsage;
import java.util.ArrayList;

public class ViewSalesHistoryCommand extends Command {
    public ViewSalesHistoryCommand(Menu menu) {
        super(menu);
        setSignature("view sales history");
        setRegex("^view sales history$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view sales history");
        getSortFieldAndDirection();
        ViewToController.sendMessageToController();
    }

    private void getSortFieldAndDirection() {
        ArrayList<String> messageInputs = new ArrayList<>();
        OutputCommands.enterSortField();
        messageInputs.add(Menu.getInputCommandWithTrim());
        OutputCommands.enterSortDirection();
        messageInputs.add(Menu.getInputCommandWithTrim());
        ViewToController.setViewMessageArrayListInputs(messageInputs);
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            //todo
        } else{
            System.out.println(serverMessage.getFirstString());
        }
    }
}
