package view.menu.loginAndRegisterMenu.Commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

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
        OutputCommands.enterPasswordOrForgot();
        String password = Menu.getInputCommandWithTrim();
        if (password.equals("forgot")) {
            forgotPassword(text);
            setNewPassword(text);
            getNewPasswordAnswer();
        } else {
            sendMessageToViewToController(text, password);

            if (ViewToController.getServerMessage().getType().equals("Successful")) {
                RegisterCommand.goToUserPanelMenu(ViewToController.getServerMessage().getFirstString(), this);
            } else {
                System.out.println(ViewToController.getServerMessage().getFirstString());
            }
        }

    }

    private void getNewPasswordAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void setNewPassword(String text) {
        String username = Arrays.asList(text.split("\\s")).get(1);

        ViewToController.setViewMessage("new password");
        ArrayList<String> messageInputs = new ArrayList<>();

        messageInputs.add(username);

        OutputCommands.enterEmailVerificationCode();
        messageInputs.add(Menu.getInputCommandWithTrim());

        OutputCommands.enterNewPassword();
        messageInputs.add(Menu.getInputCommandWithTrim());

        ViewToController.setViewMessageArrayListInputs(messageInputs);
        ViewToController.sendMessageToController();
    }

    private void forgotPassword(String text) {
        String username = Arrays.asList(text.split("\\s")).get(1);

        OutputCommands.enterEmail();
        String email = Menu.getInputCommandWithTrim();

        ViewToController.setViewMessage("forgot password");
        ViewToController.setFirstString(username);
        ViewToController.setSecondString(email);

        ViewToController.sendMessageToController();
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





