package view.menu.UserMenu.seller.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

public class ViewCompanyInformationCommand extends Command {
    public ViewCompanyInformationCommand(Menu menu) {
        super(menu);
        setSignature("view company information");
        setRegex("^view company information$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view company information");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();
        if (serverMessage.getType().equals("Successful")) {
            showCompanyInformation(serverMessage);
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }

    private void showCompanyInformation(ServerMessage serverMessage) {
        System.out.println("company name : " + serverMessage.getFirstString());
        System.out.println("company information :" + serverMessage.getSecondString());
        //todo
    }
}
