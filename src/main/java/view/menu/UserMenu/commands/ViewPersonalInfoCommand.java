package view.menu.UserMenu.commands;

import model.send.receive.ServerMessage;
import model.send.receive.UserInfo;
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
        if (serverMessage.getType().equals("Successful")) {
            showUserPersonalInfo(serverMessage);
            this.getMenu().findSubMenuWithName("personal info menu").autoExecute();
        } else {
            System.out.println(serverMessage.getFirstString());
        }

    }

    private void showUserPersonalInfo(ServerMessage serverMessage) {
        UserInfo userInfo = serverMessage.getUserInfo();

        System.out.println("username : " + userInfo.getUsername());
        System.out.println("first name : " + userInfo.getFirstName());
        System.out.println("last name : " + userInfo.getLastName());
        System.out.println("email : " + userInfo.getEmail());
        System.out.println("phone number : " + userInfo.getPhoneNumber());
        if (!userInfo.getType().equals("manager")) {
            System.out.println("money : " + userInfo.getMoney());

            if (userInfo.getType().equals("seller")) {
                System.out.println("company name : " + userInfo.getCompanyName());
                System.out.println("company information : " + userInfo.getCompanyInfo());
            }
        }

    }

    private void sendMessageToViewToController() {
        ViewToController.setViewMessage("view personal info");
        ViewToController.sendMessageToController();
    }

}