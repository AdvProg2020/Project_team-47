package view.menu.UserMenu.customer.commands;

import model.send.receive.DiscountCodeInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Date;

public class ViewDiscountCodesCommand extends Command {
    public ViewDiscountCodesCommand(Menu menu) {
        super(menu);
        setSignature("view discount codes");
        setRegex("^view discount codes$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view discount codes customer");

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            showDiscountCodes(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showDiscountCodes(ServerMessage serverMessage) {
        ArrayList<DiscountCodeInfo> discountCodeInfoArrayList = serverMessage.getDiscountCodeInfoArrayList();
        int index;
        for (DiscountCodeInfo discountCodeInfo : discountCodeInfoArrayList) {
            index = discountCodeInfoArrayList.indexOf(discountCodeInfo) + 1;
            System.out.println(index);
            System.out.println("code : " + discountCodeInfo.getCode());
            System.out.println("percent : " + discountCodeInfo.getPercent());
            System.out.println("startTime : " + discountCodeInfo.getStartTime());
            System.out.println("finishTime : " + discountCodeInfo.getFinishTime());
            System.out.println("maxUsableTime : " + discountCodeInfo.getMaxUsableTime());
            System.out.println("maxDiscountAmount : " + discountCodeInfo.getMaxDiscountAmount());
        }
    }

}