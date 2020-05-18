package view.menu.UserMenu.seller.subMenus.viewOffsMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class EditOffCommandSeller extends Command {
    public EditOffCommandSeller(Menu menu) {
        super(menu);
        setSignature("edit [offId]");
        setRegex("^edit [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage(text);
        getAnswer();
    }

    private void sendMessage(String text) {
        String offId = Arrays.asList(text.split("\\s")).get(1);
        ArrayList<String> messageInput = new ArrayList<>();
        ViewToController.setViewMessage("edit off");

        messageInput.add(offId);


    }

    private void getAnswer() {
    }
}
