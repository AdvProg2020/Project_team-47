package view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewUserCommand extends Command {
    public ViewUserCommand(Menu menu) {
        super(menu);
        setSignature("view [username]");
        setRegex("^view [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        ViewToController.setViewMessage("view user");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(Arrays.asList(text.split("\\s")).get(1));
        ViewToController.sendMessageToController();
        if (ViewToController.getServerMessage().getType().equals("Successful")) {
            viewUser(ViewToController.getServerMessage());
        } else {
            System.out.println(ViewToController.getServerMessage().getFirstString());
        }
    }

    private void viewUser(ServerMessage serverMessage) {
        //todo
    }
}
