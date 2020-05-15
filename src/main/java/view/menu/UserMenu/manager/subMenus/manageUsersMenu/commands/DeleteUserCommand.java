package view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import java.util.ArrayList;
import java.util.Arrays;

public class DeleteUserCommand extends Command {
    public DeleteUserCommand(Menu menu) {
        super(menu);
        setSignature("delete user [username]");
        setRegex("^delete user [^\\s]+$");
    }

    @Override
    public void doCommand(String text) {
        ViewToController.setViewMessage("delete user");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(Arrays.asList(text.split("\\s")).get(2));
        ViewToController.sendMessageToController();
        if (ViewToController.getServerMessage().getType().equals("successful")) {

        } else {

        }
    }
}
