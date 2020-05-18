package view.menu.UserMenu.customer.commands;

import model.send.receive.LogInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;

public class ViewOrdersCommand extends Command {
    public ViewOrdersCommand(Menu menu) {
        super(menu);
        setSignature("view orders");
        setRegex("^view orders$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view orders");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            showOrders(serverMessage);
            this.getMenu().findSubMenuWithName("orders menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showOrders(ServerMessage serverMessage) {
        ArrayList<LogInfo> logsInfo = serverMessage.getLogInfoArrayList();

        int index;
        for (LogInfo logInfo : logsInfo) {
            index = logsInfo.indexOf(logInfo);

            System.out.println(index);
            System.out.println("logId : " + logInfo.getLogId());
            System.out.println("price : " + logInfo.getPrice());
            System.out.println("status : " + logInfo.getStatus());
            System.out.println("seller : " + logInfo.getSeller());
        }
    }

}

