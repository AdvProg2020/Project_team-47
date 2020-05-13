package view.menu.loginAndRegisterMenu.Commands;

import view.ViewAttributes;
import view.command.Command;
import view.menu.Menu;
import view.menu.UserMenu.customer.CustomerPanelMenu;
import view.menu.UserMenu.manager.ManagerPanelMenu;
import view.menu.UserMenu.seller.SellerPanelMenu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputComments;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputSystemErrors;

import java.util.Arrays;
import java.util.regex.Pattern;

public class RegisterCommand extends Command {
    public RegisterCommand(Menu menu) {
        super(menu);
        setSignature("create account [type] [username]");
        setRegex("^create account (manager|seller|customer) [^ ]+$");
    }

    @Override
    public void doCommand(String text) {
        String type = Arrays.asList(text.split("\\s")).get(2);
        String username = Arrays.asList(text.split("\\s")).get(3);
        if (type.equalsIgnoreCase("manager") && ViewAttributes.isManagerRegister()) {
            OutputErrors.managerAlreadyRegistered();
        } else if (LoginCommand.checkIfHaveUsername(username)) {
            OutputErrors.usernameIsTaken();
        } else {
            createAccount(type, username);
        }
    }



    private void createAccount(String type, String username) {
        register(type, username);
        getPersonalInformation();
    }

    private void register(String type, String username) {
        OutputCommands.enterPassword();
        String password = Menu.getInputCommandWithTrim();
        if (!isPasswordValid(password)) {
            OutputErrors.invalidPassword();
        } else {
            setUserAttributes(type, username);
            ViewAttributes.setPassword(password);
            OutputComments.registerSuccessful();
            goToUserPanelMenu(type, this);
        }
    }

    private void setUserAttributes(String type, String username) {
        ViewAttributes.setUserType(type);
        ViewAttributes.setUsername(username);
        ViewAttributes.setUserSignedIn(true);
        if (!ViewAttributes.isManagerRegister() && type.equalsIgnoreCase("manager")){
            ViewAttributes.setManagerRegister(true);
        }
    }

    private void getPersonalInformation() {
    }

    private boolean isPasswordValid(String password) {
        return Pattern.compile("^[^\\s]+$").matcher(password).find();
    }

    static void goToUserPanelMenu(String type, Command command) {
        if (type.equalsIgnoreCase("customer")){
            new CustomerPanelMenu(command.getMenu()).autoExecute();
        } else if (type.equalsIgnoreCase("seller")) {
            new SellerPanelMenu(command.getMenu()).autoExecute();
        } else if (type.equalsIgnoreCase("manager")) {
            new ManagerPanelMenu(command.getMenu()).autoExecute();
        } else {
            OutputSystemErrors.invalidType();
        }
    }

}


