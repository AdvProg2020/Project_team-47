package view.menu.productMenu.subMenus.commentsMenu.commands;

import model.send.receive.ServerMessage;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;

public class AddCommentCommand extends Command {
    public AddCommentCommand(Menu menu) {
        super(menu);
        setSignature("Add comment");
        setRegex("^Add comment$");
    }

    @Override
    public void doCommand(String text) {
        sendMessage();
        getAnswer();
    }

    private void sendMessage() {
        ViewToController.setViewMessage("add comment");

        OutputCommands.enterTitle();
        ViewToController.setFirstString(Menu.getInputCommandWithTrim());

        OutputCommands.enterContent();
        ViewToController.setSecondString(Menu.getInputCommandWithTrim());

        ViewToController.sendMessageToController();
    }

    private void getAnswer() {
        ServerMessage serverMessage = ViewToController.getServerMessage();

        if (serverMessage.getType().equals("Successful")) {
            //unsure
        } else {
            System.out.println(serverMessage.getFirstString());
        }
    }
}
