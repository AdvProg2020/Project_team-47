package view.menu.loginAndRegister.Commands;

import view.UserAttributes;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;

import java.util.Arrays;

public class LoginCommand extends Command {
    public LoginCommand(Menu menu) {
        super(menu);
        setSignature("login [username]");
        setRegex("^login [^ ]+$");
    }

    @Override
    public void doCommand(String text) {
        String username = Arrays.asList(text.split("\\s")).get(1);
        if (!checkIfHaveUsername(username)){
            OutputErrors.usernameNotExist();
        } else {
            OutputCommands.enterPassword();
            String password = Menu.getInputCommand();
            if (!checkIfPasswordIsCorrectFromController(password)){
                OutputErrors.incorrectPassword();
            }
            else {
                login(username);
            }
        }
    }

    private void login(String username) {
        setUserAttributes(username);
        OutputComments.loginSuccessful();
        RegisterCommand.goToUserPanelMenu(UserAttributes.getType(), this);
    }

    private String getTypeFromController(String username) {
        return null;
    }

    private void setUserAttributes(String username) {
        UserAttributes.setUsername(username);
        UserAttributes.setSignedIn(true);
        UserAttributes.setType(getTypeFromController(username));
    }

    private boolean checkIfPasswordIsCorrectFromController(String password) {
        return true;
    }

    static boolean checkIfHaveUsername(String username) {
        return false;
    }
}





