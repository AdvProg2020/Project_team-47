package controller;


import model.user.Seller;
import model.user.User;

import java.util.regex.Pattern;

public class UserPanelController extends Controller {
    public static void personalInfo() {
        sendAnswer(loggedUser.userInfoForSending());
    }

    public static void edit(String field, String newValue) {
        if (newValue.length() == 0) {
            sendError("Enter a value to change!!");
        }
        switch (field) {
            case "first-name":
                loggedUser.setFirstName(newValue);
                actionCompleted();
                break;
            case "last-name":
                loggedUser.setLastName(newValue);
                actionCompleted();
                break;
            case "phone-number":
                editPhoneNumber(newValue);
                break;
            case "password":
                editPassword(newValue);
                break;
            case "company-name":
                if (loggedUser instanceof Seller) {
                    ((Seller) loggedUser).setCompanyName(newValue);
                    actionCompleted();
                    break;
                }
            case "company-info":
                if (loggedUser instanceof Seller) {
                    ((Seller) loggedUser).setCompanyInfo(newValue);
                    actionCompleted();
                    break;
                }
            default:
            wrongEditFiled();
        }
        loggedUser.updateDatabase().update();
    }

    private static void wrongEditFiled() {
        StringBuilder errorText = new StringBuilder("Your entered wrong filed to edit!!\n" +
                "You can edit:\n");
        ArrayList<String> editableField = new ArrayList<>();
        editableField.add("first-name");
        editableField.add("last-name");
        editableField.add("phone-number");
        editableField.add("password");
        if (loggedUser instanceof Seller) {
            editableField.add("company-name");
            editableField.add("company-info");
        }
        for (int i = 0; i < editableField.size(); i++) {
            errorText.append(i+1).append(": ").append(editableField.get(i)).append("\n");
        }
        sendError(errorText.toString());
    }
    private static void editPhoneNumber(String newPhoneNumber) {
        if (!User.isPhoneValid(newPhoneNumber)) {
            sendError("Wrong phone number!!");
        }
        String lastPhoneNumber = loggedUser.getPhoneNumber();
        loggedUser.setPhoneNumber("");
        if (User.isThereUserWithPhone(newPhoneNumber)) {
            loggedUser.setPhoneNumber(lastPhoneNumber);
            sendError("There is another user with this phone number!!");
        } else {
            loggedUser.setPhoneNumber(newPhoneNumber);
            actionCompleted();
        }
    }

    private static void editPassword(String newPassword) {
        if (!User.isPasswordValid(newPassword)) {
            sendError("Password is not valid!!");
        } else {
            loggedUser.setPassword(newPassword);
            actionCompleted();
        }
    }

    static boolean checkSort(String sortField, String direction, String itemToSort) {
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
}//end UserPanelController class
