package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import model.send.receive.OffInfo;
import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.Arrays;

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
            showOffInfo(serverMessage.getOffInfo());
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showOffInfo(OffInfo offInfo) {
        //todo
    }
}
