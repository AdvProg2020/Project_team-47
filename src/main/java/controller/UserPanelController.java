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
                break;
            case "last-name":
                loggedUser.setLastName(newValue);
                break;
            case "phone-number":
                editPhoneNumber(newValue);
                break;
            case "email":
                editEmail(newValue);
                break;
            case "password":
                editPassword(newValue);
                break;
            case "company-name":
                if (loggedUser instanceof Seller) {
                    ((Seller) loggedUser).setCompanyName(newValue);
                    break;
                }
            case "company-info":
                if (loggedUser instanceof Seller) {
                    ((Seller) loggedUser).setCompanyInfo(newValue);
                    break;
                }
            default:
                sendError("Your entered wrong filed to edit!!");
        }
    }

    private static void editEmail(String newEmail) {
        if (!User.isEmailValid(newEmail)) {
            sendError("Wrong email!!");
        } else {
            loggedUser.setEmail(newEmail);
        }
    }

    private static void editPhoneNumber(String newPhoneNumber) {
        if (!User.isPhoneValid(newPhoneNumber)) {
            sendError("Wrong phone number!!");
        } else {
            loggedUser.setPhoneNumber(newPhoneNumber);
        }
    }

    private static void editPassword(String newPassword) {
        if (!User.isPasswordValid(newPassword)) {
            sendError("Password is not valid!!");
        } else {
            loggedUser.setPassword(newPassword);
        }
    }

    static boolean checkSort(String sortFiled, String direction, String itemToSort) {
        if (sortFiled == null && direction == null) {
            return true;
        } else if (sortFiled == null || direction == null) {
            return false;
        } else if (!Pattern.matches("(descending|ascending)", direction)) {
            return false;
        }
        switch (itemToSort) {
            case "log":
                return sortFiled.equals("money");
            case "request":
                return Pattern.matches("(apply-date|sender-username)", sortFiled);
            case "user":
                return Pattern.matches("(first-name|last-name|username)", sortFiled);
            case "off":
            case "discount-code":
                return Pattern.matches("(start-time|finish-time|percent)", sortFiled);
            case "product":
                return Pattern.matches("(name|score|seen-time|date)", sortFiled);
            case "category":
                return sortFiled.equals("name");
        }
        return false;
    }//done
}
