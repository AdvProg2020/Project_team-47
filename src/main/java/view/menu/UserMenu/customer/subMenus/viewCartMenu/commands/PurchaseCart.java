package view.menu.UserMenu.customer.subMenus.viewCartMenu.commands;

import view.ViewAttributes;
import view.command.Command;
import view.menu.Menu;
import view.menu.UserMenu.customer.subMenus.viewCartMenu.subMenus.ReceiverInformationMenu;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputQuestions;

public class PurchaseCart extends Command {
    public PurchaseCart(Menu menu) {
        super(menu);
        setSignature("purchase");
        setRegex("^purchase");
    }

    @Override
    public void doCommand(String text) {
        if (!ViewAttributes.isUserSignedIn()){
            notSignedIn();

        } else {
            new ReceiverInformationMenu("receiver information", this.menu).manualExecute();
        }
    }

    private void notSignedIn() {
        OutputErrors.notSignedIn();
        OutputQuestions.goToSignInMenu();
        String answer = Menu.getInputCommandWithTrim();
        if (answer.equalsIgnoreCase("no")){

        } else if (answer.equalsIgnoreCase("yes")){
            goToSignInMenu();
        } else {
            OutputErrors.invalidInputCommand();
            notSignedIn();
        }
    }

    private void goToSignInMenu() {
    }
}
