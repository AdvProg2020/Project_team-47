package view.menu.UserMenu.commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;

import javax.swing.text.View;

public class GoToOffsMenuCommand extends Command {
    public GoToOffsMenuCommand(Menu menu) {
        super(menu);
        setSignature("offs");
        setRegex("^offs$");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController();
        if (ViewToController.getServerMessage().getType().equals("successful")) {
            this.menu.findSubMenuWithName("all offs menu").autoExecute();
        } else {
            System.out.println(ViewToController.getServerMessage().getFirstString());

        }
    }
    public void sendMessageToViewToController() {
        ViewToController.setViewMessage("offs");
        ViewToController.sendMessageToController();
    }
}
