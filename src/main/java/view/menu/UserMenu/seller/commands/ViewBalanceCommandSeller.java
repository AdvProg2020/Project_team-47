package view.menu.UserMenu.seller.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputComments;

public class ViewBalanceCommandSeller extends Command {

    public ViewBalanceCommandSeller(Menu menu) {
        super(menu);
        setSignature("view balance");
        setRegex("^view balance$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("view balance seller");
        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            OutputComments.yourBalance();
            System.out.println(serverMessage.getNumber());
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
