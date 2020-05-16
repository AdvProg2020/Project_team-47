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
            sendAnswer(loggedUser.getType());
        }
    }

    public static void register(HashMap<String, String> registerInformationHashMap) {
        //this function will check some error may occur during register and if
        //there wasn't any error it will call registerUser function to completing register

        if (checkRegisterInfoKey(registerInformationHashMap)) {
            sendError("Not enough information!!");
        } else if (registerInformationHashMap.get("type").equals("manager") && User.isThereManager()) {
            sendError("There is manager and you can't register as a manager!!");
        } else if (!registerInformationHashMap.get("type").equals("manager") && !User.isThereManager()) {
            sendError("There isn't a manager and you should register as a manager!!");
        } else if (registerInformationIsValid(registerInformationHashMap)) {
            registerUser(registerInformationHashMap, registerInformationHashMap.get("type"));
        }
    }

    static boolean checkRegisterInfoKey(HashMap<String, String> registerInfo) {
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

    private static boolean registerInformationIsValid(HashMap<String, String> userInfo) {
        if (!User.isUsernameValid(userInfo.get("username"))) {
            sendError("Username isn't valid!!\nit should contains " +
                    "more than five letters and only letters and numbers");
        } else if (User.doesUsernameUsed(userInfo.get("username"))) {
            sendError("There is user with this username already!!");
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
            default:
                sendError("Please enter valid type!!");
                return;
        }
        newUser.emailVerification();
        actionCompleted();
    }

    public static void confirmEmail(String username, String password, String verificationCode) {
        User user = User.getUserInVerificationList(username);
        if (user == null) {
            sendError("There isn't any user with this username in verification list!!");
        } else if (!user.checkPasswordIsCorrect(password)) {
            sendError("Password isn't correct!!");
        } else if (!user.doesCodeSend()) {
            sendError("We didn't send any code to your email!!");
        } else if (!user.sendCodeIsCorrect(verificationCode)) {
            sendError("Incorrect code!!");
        } else {
            user.confirmEmail();
            loggedUser = user;
            actionCompleted();
        }
    }

    public static void forgotPassword(String username, String email) {
        User user = User.getUserByUsername(username);
        if (user == null || !user.checkEmail(email)) {
            sendError("There isn't any user with this username or email!!");
        } else {
            user.sendForgotPasswordCode();
            actionCompleted();
        }
    }

    public static void newPassword(String username, String code,String newPassword) {
        User user = User.getUserByUsername(username);
        if (user == null) {
            sendError("There isn't any user with this username!!");
        } else if (!user.doesCodeSend()) {
            sendError("Error!!");
        } else if (!user.sendCodeIsCorrect(code)) {
            sendError("Incorrect code!!");
        } else if (!User.isPasswordValid(newPassword)) {
            sendError("Password isn't valid!!");
        } else {
            user.setSendCode("");
            user.setPassword(newPassword);
        }
    }

    public static void logout() {
        if (loggedUser == null) {
            sendError("You aren't logged in!!");
        } else {
            loggedUser = null;
            ShoppingCart.setLocalShoppingCart(new ShoppingCart());
            actionCompleted();
        }
    }

}//end LoginController class
