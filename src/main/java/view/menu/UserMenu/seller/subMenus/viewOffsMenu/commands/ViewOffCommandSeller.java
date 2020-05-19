package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import model.send.receive.OffInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");

        System.out.println("startTime : " + dateFormat.format(offInfo.getStartTime()));
        System.out.println("finishTime : " + dateFormat.format(offInfo.getFinishTime()));

        System.out.println("products");
        HashMap<String, String> productsNameAndId = offInfo.getProductsNameId();

        for (Map.Entry<String, String> entry : productsNameAndId.entrySet()) {
            String name = entry.getKey();
            String id = entry.getValue();

            System.out.println("name : " + name + " id : " + id);
        }

    }
}
