package controller.panels;

import controller.Command;
import controller.Error;
import model.category.Category;
import model.send.receive.ClientMessage;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static controller.Controller.*;

public abstract class UserPanelCommands extends Command {

    public static ViewPersonalInfoCommand getViewPersonalInfoCommand() {
        return ViewPersonalInfoCommand.getInstance();
    }

    public static EditFieldCommand getEditFieldCommand() {
        return EditFieldCommand.getInstance();
    }

    public static boolean checkSort(String sortField, String direction, String itemToSort) {
        //this function will check that items could sort by the given field and direction

        if (sortField == null && direction == null) {
            return true;
        } else if (sortField == null || direction == null) {
            return false;
        } else if (!Pattern.matches("(descending|ascending)", direction)) {
            return false;
        }

        switch (itemToSort) {
            case "log":
                return sortField.equals("money");
            case "request":
                return Pattern.matches("(apply-date|sender-username)", sortField);
            case "user":
                return Pattern.matches("(first-name|last-name|username)", sortField);
            case "off":
            case "discount-code":
                return Pattern.matches("(start-time|finish-time|percent)", sortField);
            case "product":
                return Pattern.matches("(name|score|seen-time|price)", sortField);
            case "category":
                return sortField.equals("name");
        }
        return false;
    }

    public static boolean checkRegisterInfoKey(HashMap<String, String> registerInfo) {
        //this function will check that if registerInfo HashMap contains all key that should have or not

        String[] registerKey = {"username", "password", "first-name", "last-name", "email", "phone-number", "type"};
        for (String key : registerKey) {
            if (!registerInfo.containsKey(key))
                System.out.println(key);
            return false;
        }

        String[] sellerKey = {"company-info", "company-name"};
        if (registerInfo.get("type").equals("seller")) {
            for (String key : sellerKey) {
                if (!registerInfo.containsKey(key))
                    return false;
            }
        }
        return true;
    }

    public static boolean isThereProperty(Category category, String property) {
        for (String specialProperty : category.getSpecialProperties()) {
            if (specialProperty.equalsIgnoreCase(property))
                return true;
        }
        return false;
    }
}


class ViewPersonalInfoCommand extends UserPanelCommands {
    private static ViewPersonalInfoCommand command;


    private ViewPersonalInfoCommand() {
        this.name = "view personal info";
    }

    public static ViewPersonalInfoCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewPersonalInfoCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return;
        }
        personalInfo();
    }


    public void personalInfo() {
        sendAnswer(getLoggedUser().userInfoForSending());
    }

}//end ViewPersonalInfoCommand Class


class EditFieldCommand extends UserPanelCommands {
    private static EditFieldCommand command;

    private EditFieldCommand() {
        this.name = "edit personal info";
    }

    public static EditFieldCommand getInstance() {
        if (command != null)
            return command;
        command = new EditFieldCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return;
        }
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1)))
            return;

        edit(reqInfo.get(0).toLowerCase(), reqInfo.get(1));
    }


    public void edit(String field, String newValue) {
        if (newValue.length() == 0) {
            sendError("Enter a value to change!!");
        }
        switch (field) {
            case "first-name":
                getLoggedUser().setFirstName(newValue);
                actionCompleted();
                break;
            case "last-name":
                getLoggedUser().setLastName(newValue);
                actionCompleted();
                break;
            case "phone-number":
                editPhoneNumber(newValue);
                break;
            case "password":
                editPassword(newValue);
                break;
            case "company-name":
                if (getLoggedUser() instanceof Seller) {
                    editCompanyName(newValue);
                    break;
                }
            case "company-info":
                if (getLoggedUser() instanceof Seller) {
                    editCompanyInfo(newValue);
                    break;
                }
            default:
                wrongEditFiled();
        }
        getLoggedUser().updateDatabase().update();
    }


    private void editCompanyName(String newName) {
        ((Seller) getLoggedUser()).setCompanyName(newName);
        actionCompleted();
    }

    private void editCompanyInfo(String newInfo) {
        ((Seller) getLoggedUser()).setCompanyInfo(newInfo);
        actionCompleted();
    }

    private void editPhoneNumber(String newPhoneNumber) {
        if (!User.isPhoneValid(newPhoneNumber)) {
            sendError(Error.UNVALID_PHONE.getError());
        }
        String lastPhoneNumber = getLoggedUser().getPhoneNumber();
        getLoggedUser().setPhoneNumber("");
        if (User.isThereUserWithPhone(newPhoneNumber)) {
            getLoggedUser().setPhoneNumber(lastPhoneNumber);
            sendError(Error.REPEATED_PHONE.getError());
        } else {
            getLoggedUser().setPhoneNumber(newPhoneNumber);
            actionCompleted();
        }
    }

    private void editPassword(String newPassword) {
        if (!User.isPasswordValid(newPassword)) {
            sendError("Password is not valid!!");
        } else {
            getLoggedUser().setPassword(newPassword);
            actionCompleted();
        }
    }

    private void wrongEditFiled() {
        StringBuilder errorText = new StringBuilder("Your entered wrong filed to edit!!\n" +
                "You can edit:\n");
        ArrayList<String> editableField = new ArrayList<>();
        editableField.add("first-name");
        editableField.add("last-name");
        editableField.add("phone-number");
        editableField.add("password");
        if (getLoggedUser() instanceof Seller) {
            editableField.add("company-name");
            editableField.add("company-info");
        }
        for (int i = 0; i < editableField.size(); i++) {
            errorText.append(i + 1).append(": ").append(editableField.get(i)).append("\n");
        }
        sendError(errorText.toString());
    }

}



