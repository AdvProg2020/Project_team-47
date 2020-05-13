package view.menu.loginAndRegisterMenu.Commands;

import view.ViewAttributes;
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
        if (!ViewAttributes.isUserSignedIn()) {
            OutputErrors.notSignedIn();
        } else {
            resetUserAttributes();
            OutputComments.logoutSuccessful();
            new CustomerPanelMenu(this.menu).autoExecute();
        }
    }

    private void resetUserAttributes() {
        ViewAttributes.setUserType("customer");
        ViewAttributes.setPassword(null);
        ViewAttributes.setUsername(null);
        ViewAttributes.setUserSignedIn(false);
    }
}
