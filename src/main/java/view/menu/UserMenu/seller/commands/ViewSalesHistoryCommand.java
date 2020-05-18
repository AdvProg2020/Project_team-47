package view.menu.UserMenu.seller.commands;

import model.send.receive.LogInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Date;

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
            showSalesHistory(serverMessage);
        } else{
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showSalesHistory(ServerMessage serverMessage) {
        ArrayList<LogInfo> salesHistory = serverMessage.getLogInfoArrayList();
        int index;
        for (LogInfo saleHistory : salesHistory) {
            index = salesHistory.indexOf(saleHistory) + 1;
            System.out.println(index + ".");
            System.out.println("logId : " + saleHistory.getLogId());
            System.out.println("customer : " + saleHistory.getCustomer());
            System.out.println("price : " + saleHistory.getPrice());
            System.out.println("logDate : " + saleHistory.getLogDate());
            System.out.println("status : " + saleHistory.getStatus());
        }
    }
}
