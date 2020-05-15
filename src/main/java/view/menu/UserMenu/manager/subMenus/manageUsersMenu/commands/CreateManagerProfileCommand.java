package view.menu.UserMenu.manager.subMenus.manageUsersMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreateManagerProfileCommand extends Command {
    public CreateManagerProfileCommand(Menu menu) {
        super(menu);
        setSignature("create manager profile");
        setRegex("^create manager profile$");
    }

    @Override
    public void doCommand(String text) {
        ViewToController.setViewMessage("create manager profile");
        getInformation();

        ViewToController.sendMessageToController();
        if (ViewToController.getServerMessage().getType().equals("successful")) {

        } else {

        }
    }

    private void getInformation() {
        HashMap<String, String> messageInputs = new HashMap<>();
        OutputCommands.enterUsername();
        messageInputs.put("username", Menu.getInputCommandWithTrim());
        OutputCommands.enterPassword();
        messageInputs.put("password", Menu.getInputCommandWithTrim());
        OutputCommands.enterFirstName();
        messageInputs.put("first-name", Menu.getInputCommandWithTrim());
        OutputCommands.enterLastName();
        messageInputs.put("last-name", Menu.getInputCommandWithTrim());
        OutputCommands.enterEmail();
        messageInputs.put("email", Menu.getInputCommandWithTrim());
        OutputCommands.enterPhoneNumber();
        messageInputs.put("phone-number", Menu.getInputCommandWithTrim());
    }


}
