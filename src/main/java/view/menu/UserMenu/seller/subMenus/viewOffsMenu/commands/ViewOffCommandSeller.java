package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import model.send.receive.OffInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.*;

public class ViewOffCommandSeller extends Command {
    public ViewOffCommandSeller(Menu menu) {
        super(menu);
        setSignature("view [offId]");
        setRegex("^view [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String offId = Arrays.asList(text.split("\\s")).get(1);

        ViewToController.setViewMessage("view off");
        ViewToController.setFirstString(offId);

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            showOffInfo(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showOffInfo(ServerMessage serverMessage) {
        OffInfo offInfo = serverMessage.getOffInfo();
        System.out.println("offId : " + offInfo.getOffId());
        System.out.println("percent : " + offInfo.getPercent());
        System.out.println("sellerUsername : " + offInfo.getSellerUsername());
        System.out.println("offStatus : " + offInfo.getOffStatus());
        //todo
        //System.out.println("startTime : " + offInfo.get);
        //System.out.println("finishTime : " + offInfo.get);

        System.out.println("products");
        HashMap<String, String> a = offInfo.getProductsNameId();

        String[] ids = (String[]) a.values().toArray();
        String[] names = (String[])a.keySet().toArray();

        for (int i = 0; i < a.size(); i++) {
            System.out.println("name : " + names [i] + "id : " + ids [i]);
        }
    }
}
