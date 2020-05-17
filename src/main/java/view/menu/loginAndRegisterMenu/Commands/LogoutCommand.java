package view.menu.loginAndRegisterMenu.Commands;

import view.ViewAttributes;
import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.UserMenu.customer.CustomerPanelMenu;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;

public class LogoutCommand extends Command {
    public LogoutCommand(Menu menu) {
        super(menu);
        setSignature("logout");
        setRegex("logout");
    }

    @Override
    public void doCommand(String text) {
        sendMessageToViewToController();
        if (ViewToController.getServerMessage().getType().equals("Successful")) {
            new CustomerPanelMenu(this.menu).autoExecute();
        } else {
            System.out.println(ViewToController.getServerMessage().getFirstString());
        }
    }

    private void sendMessageToViewToController() {
        ViewToController.setViewMessage("logout");
        ViewToController.sendMessageToController();
    }


}
