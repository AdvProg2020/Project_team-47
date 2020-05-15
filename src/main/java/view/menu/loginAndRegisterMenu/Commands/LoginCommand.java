package view.menu.loginAndRegisterMenu.Commands;

import view.ViewAttributes;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginCommand extends Command {
    public LoginCommand(Menu menu) {
        super(menu);
        setSignature("login [username]");
        setRegex("^login [^ ]+$");
    }

    @Override
    public void doCommand(String text) {
        OutputCommands.enterPassword();
        String password = Menu.getInputCommandWithTrim();
        sendMessageToViewToController(text, password);
        if (ViewToController.getServerMessage().getType().equals("successful")) {
            RegisterCommand.goToUserPanelMenu(ViewToController.getServerMessage().getFirstString(), this);
        } else {
            System.out.println(ViewToController.getServerMessage().getFirstString());
        }
    }


    private void sendMessageToViewToController(String text, String password) {
        ViewToController.setViewMessage("login");
        ArrayList<String> messageInputs = new ArrayList<>();
        messageInputs.add(Arrays.asList(text.split("\\s")).get(1));
        messageInputs.add(password);
        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }
}





