package view.menu.User.commands;

import view.UserAttributes;
import view.command.Command;
import view.menu.Menu;
import view.outputMessages.OutputComments;
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
        if (!UserAttributes.isSignedIn()){
            OutputErrors.notSignedIn();
            checkIfUserWantToSignIn();
        } else {
            menu.findSubMenuWithName("personal info menu").autoExecute();
        }
    }

    private void checkIfUserWantToSignIn() {
        OutputQuestions.goToSignInMenu();
        String answer = Menu.getInputCommand();
        if (answer.equalsIgnoreCase("no")) {

        } else if (answer.equalsIgnoreCase("yes")) {
            // todo
        } else {
            OutputErrors.invalidInputCommand();
            checkIfUserWantToSignIn();
        }
    }

}
