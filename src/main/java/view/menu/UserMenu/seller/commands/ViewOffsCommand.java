package view.menu.UserMenu.seller.commands;

import model.send.receive.OffInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;

public class ViewOffsCommand extends Command {
    public ViewOffsCommand(Menu menu) {
        super(menu);
        setSignature("view offs");
        setRegex("^view offs$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view offs");
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
            showOffsInfo(serverMessage);
            this.getMenu().findSubMenuWithName("view offs menu");
        } else {
            System.out.println(serverMessage.getFirstString());
        }

    }

    private void showOffsInfo(ServerMessage serverMessage) {
        ArrayList<OffInfo> offInfoArrayList = serverMessage.getOffInfoArrayList();
        int offIndex;
        for (OffInfo offInfo : offInfoArrayList) {
            offIndex = offInfoArrayList.indexOf(offInfo) + 1;
            System.out.println(offIndex + ".");
            System.out.println("offId : " + offInfo.getOffId());
            System.out.println("percent : " + offInfo.getPercent());
            System.out.println("startTime : " + offInfo.getStartTime());
            System.out.println("finishTime : " + offInfo.getFinishTime());
            System.out.println("offStatus : " + offInfo.getOffStatus());
        }
    }
}
