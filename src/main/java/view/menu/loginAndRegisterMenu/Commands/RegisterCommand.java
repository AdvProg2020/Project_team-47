package view.menu.loginAndRegisterMenu.Commands;

import view.ViewToController;
import view.command.Command;
import view.menu.Menu;
import view.menu.UserMenu.customer.CustomerPanelMenu;
import view.menu.UserMenu.manager.ManagerPanelMenu;
import view.menu.UserMenu.seller.SellerPanelMenu;
import view.outputMessages.OutputCommands;
import view.outputMessages.OutputErrors;
import view.outputMessages.OutputSystemErrors;

import java.util.Arrays;
import java.util.HashMap;
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
        sendMessageToViewToController(text);
        if (ViewToController.getServerMessage().getType().equals("successful")) {
            goToUserPanelMenu(type, this);
        } else {
            System.out.println(ViewToController.getServerMessage().getFirstString());
        }
    }

    private void sendMessageToViewToController(String text) {
        ViewToController.setViewMessage("register");
        String type = Arrays.asList(text.split("\\s")).get(2);
        String username = Arrays.asList(text.split("\\s")).get(3);
        register(type, username);
    }

    //"username", "password", "first-name", "last-name", "email", "phone-number", "type"

    private void register(String type, String username) {
        OutputCommands.enterPassword();
        String password = Menu.getInputCommandWithTrim();
        if (!isPasswordValid(password)) {
            OutputErrors.invalidPassword();
            register(type, username);
        } else {
            getPersonalInformation(username, type, password);
        }
    }

    private void getPersonalInformation(String username, String type, String password) {
        OutputCommands.enterFirstName();
        String firstName = Menu.getInputCommandWithTrim();
        OutputCommands.enterLastName();
        String lastName = Menu.getInputCommandWithTrim();
        OutputCommands.enterEmail();
        String email = Menu.getInputCommandWithTrim();
        OutputCommands.enterPhoneNumber();
        String phoneNumber = Menu.getInputCommandWithTrim();
        if (isEmailValid()) {
            HashMap<String, String> viewMessageHashMapInputs = new HashMap<>();
//            ArrayList<String> viewMessageArrayListInput = new ArrayList<>();
            viewMessageHashMapInputs.put("username", username);
            viewMessageHashMapInputs.put("password", password);
            viewMessageHashMapInputs.put("first-name", firstName);
            viewMessageHashMapInputs.put("last-name", lastName);
            viewMessageHashMapInputs.put("email", email);
            viewMessageHashMapInputs.put("phone-number", phoneNumber);
            viewMessageHashMapInputs.put("type", type);
//            viewMessageArrayListInput.add(type);
            ViewToController.setViewMessageFirstHashMapInputs(viewMessageHashMapInputs);
//            ViewToController.setViewMessageArrayListInputs(viewMessageArrayListInput);
            ViewToController.sendMessageToController();
        }
        else {
            OutputErrors.invalidEmail();
            getPersonalInformation(username, type, password);
        }
    }

    private boolean isEmailValid() {
        return true;
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


