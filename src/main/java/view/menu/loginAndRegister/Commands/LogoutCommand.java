package view.menu.loginAndRegister.Commands;

import view.UserAttributes;
import view.command.Command;
import view.menu.Menu;
import view.menu.User.customer.CustomerPanelMenu;
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
        if (!UserAttributes.isSignedIn()) {
            OutputErrors.notSignedIn();
        } else {
            resetUserAttributes();
            OutputComments.logoutSuccessful();
            new CustomerPanelMenu("customer panel menu", this.menu).autoExecute();
        }
    }

    private void resetUserAttributes() {
        UserAttributes.setType("customer");
        UserAttributes.setPassword(null);
        UserAttributes.setUsername(null);
        UserAttributes.setSignedIn(false);
    }
}
