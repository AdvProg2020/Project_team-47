package controller;

import model.others.ShoppingCart;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;
import model.user.User;

import java.util.HashMap;

public class LoginController extends Controller {

    public static void login(String username, String password) {
        if (!User.isUsernameValid(username)) {
            sendError("Username isn't valid!!");
        } else if (!User.isPasswordValid(password)) {
            sendError("Password isn't valid!!");
        } else if (!User.isThereUserWithUsername(username) ||
                !User.checkPasswordIsCorrect(username, password)) {
            sendError("There isn't any user with this username or password!!");
        } else {
            loggedUser = User.getUserByUsername(username);
            if (loggedUser instanceof Customer) {
                ((Customer) loggedUser).getShoppingCart().mergingWithLocalCart(ShoppingCart.getLocalShoppingCart());
            }
            sendAnswer("You are logged in successfully.");
        }
    }

    public static void register(HashMap<String, String> registerInformationHashMap) {
        if (!registerInformationHashMap.containsKey("username") ||
                !registerInformationHashMap.containsKey("password") ||
                !registerInformationHashMap.containsKey("first-name") ||
                !registerInformationHashMap.containsKey("last-name") ||
                !registerInformationHashMap.containsKey("email") ||
                !registerInformationHashMap.containsKey("phone-number") ||
                !registerInformationHashMap.containsKey("type")) {
            sendError("Not enough information!!");
        } else if (registerInformationHashMap.get("type").equals("seller") && (
                !registerInformationHashMap.containsKey("company-name") ||
                        !registerInformationHashMap.containsKey("company-info"))) {
            sendError("Not enough information!!");
        } else if (registerInformationHashMap.get("type").equals("manager") && User.isThereManager()) {
            sendError("There is manager and you can't register as a manager!!");
        } else {
            registerUser(registerInformationHashMap, registerInformationHashMap.get("type"));
        }
    }

    private static boolean registerInformationIsValid(HashMap<String, String> userInfo) {
        if (!User.isUsernameValid(userInfo.get("username"))) {
            sendError("Username isn't valid!!");
        } else if (User.isThereUserWithUsername(userInfo.get("username"))) {
            sendError("This username isn't valid.");
        } else if (!User.isEmailValid(userInfo.get("email"))) {
            sendError("Email isn't valid!!");
        } else if (!User.isPhoneValid(userInfo.get("phone-number"))) {
            sendError("Phone number isn't valid!!");
        } else if (User.isThereUserWithEmail(userInfo.get("email"))) {
            sendError("There is a user with this email already!!");
        } else if (User.isThereUserWithPhone(userInfo.get("phone-number"))) {
            sendError("There is a user with this phone number already!!");
        } else if (!User.isPasswordValid(userInfo.get("password"))) {
            sendError("Password isn't valid!!");
        } else {
            return true;
        }
        return false;
    }

    static void registerUser(HashMap<String, String> userInformation, String userType) {
        if (!registerInformationIsValid(userInformation)) {
            return;
        }
        User newUser;
        switch (userType) {
            case "customer":
                newUser = new Customer(userInformation);
                break;
            case "seller":
                newUser = new Seller(userInformation);
                break;
            case "manager":
                newUser = new Manager(userInformation);
                break;
        }
        sendAnswer("Your registered successfully.");
    }

    public static void logout() {
        if (loggedUser == null) {
            sendError("You aren't logged in!!");
        } else {
            loggedUser = null;
            ShoppingCart.setLocalShoppingCart(new ShoppingCart());
            sendAnswer("You logout successfully.");
        }
    }

}
