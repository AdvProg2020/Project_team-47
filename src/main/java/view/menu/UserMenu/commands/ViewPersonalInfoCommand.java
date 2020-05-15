package view.menu.UserMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewAttributes;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

public class ViewPersonalInfoCommand extends Command {
    public ViewPersonalInfoCommand(Menu menu) {
        super(menu);
        setSignature("view personal info");
        setRegex("^view personal info$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController();
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("successful")) {
            this.getMenu().findSubMenuWithName("product menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }

    }

    private void sendMessageToViewToController() {
        ViewToController.setViewMessage("view personal info");
        ViewToController.sendMessageToController();
    }

//    private void checkIfUserWantToSignIn() {
//        OutputQuestions.goToSignInMenu();
//        String answer = Menu.getInputCommandWithTrim();
//        if (answer.equalsIgnoreCase("no")) {
//
//        } else if (answer.equalsIgnoreCase("yes")) {
//            // todo
//        } else {
//            OutputErrors.invalidInputCommand();
//            checkIfUserWantToSignIn();
//        }
//    }

}
