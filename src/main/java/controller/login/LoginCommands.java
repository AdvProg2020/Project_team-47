package controller.login;

import controller.Command;
import controller.Error;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkRegisterInfoKey;

public abstract class LoginCommands extends Command {

    public static RegisterCommand getRegisterCommand() {
        return RegisterCommand.getInstance();
    }

    public static LoginCommand getLoginCommand() {
        return LoginCommand.getInstance();
    }

    public static ConfirmEmailCommand getConfirmEmailCommand() {
        return ConfirmEmailCommand.getInstance();
    }

    public static ForgotPasswordCommand getForgotPasswordCommand() {
        return ForgotPasswordCommand.getInstance();
    }

    public static NewPasswordCommand getNewPasswordCommand() {
        return NewPasswordCommand.getInstance();
    }

    public static LogoutCommand getLogoutCommand() {
        return LogoutCommand.getInstance();
    }

}


class RegisterCommand extends LoginCommands {
    private static RegisterCommand command;

    private RegisterCommand() {
        this.name = "register";
    }

    public static RegisterCommand getInstance() {
        if (command != null)
            return command;
        command = new RegisterCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstHashMap()))
            return;
        register(request.getFirstHashMap());
    }


    public void register(HashMap<String, String> registerInformationHashMap) {
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


    private boolean registerInformationIsValid(HashMap<String, String> userInfo) {
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


    private void registerUser(HashMap<String, String> userInformation, String userType) {
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

}//end RegisterCommand Class


class LoginCommand extends LoginCommands {
    private static LoginCommand command;

    private LoginCommand() {
        this.name = "login";
    }

    public static LoginCommand getInstance() {
        if (command != null)
            return command;
        command = new LoginCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1)))
            return;

        login(reqInfo.get(0), reqInfo.get(1));
    }


    public void login(String username, String password) {
        if (!User.isUsernameValid(username)) {
            sendError(Error.USERNAME_NOT_VALID.getError());
        } else if (!User.isPasswordValid(password)) {
            sendError(Error.PASSWORD_NOT_VALID.getError());
        } else if (!User.isThereUserWithUsername(username) ||
                !User.checkPasswordIsCorrect(username, password)) {
            sendError(Error.USER_PASS_NOT_EXIST.getError());
        } else {
            setLoggedUser(User.getUserByUsername(username));
            if (getLoggedUser() instanceof Customer) {
                ((Customer) getLoggedUser()).getShoppingCart().mergingWithLocalCart(ShoppingCart.getLocalShoppingCart());
            }
            sendAnswer(getLoggedUser().getType());
        }
    }

}//end Login command Class


class ConfirmEmailCommand extends LoginCommands {
    private static ConfirmEmailCommand command;

    private ConfirmEmailCommand() {
        this.name = "confirm email";
    }


    public static ConfirmEmailCommand getInstance() {
        if (command != null)
            return command;
        command = new ConfirmEmailCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)))
            return;
        confirmEmail(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2));
    }


    private void confirmEmail(String username, String password, String verificationCode) {
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
            setLoggedUser(user);
            actionCompleted();
        }
    }

}//end ConfirmEmailCommand Class


class ForgotPasswordCommand extends LoginCommands {
    private static ForgotPasswordCommand command;

    private ForgotPasswordCommand() {
        this.name = "forgot password";
    }


    public static ForgotPasswordCommand getInstance() {
        if (command != null)
            return command;
        command = new ForgotPasswordCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString(), request.getSecondString()))
            return;

        forgotPassword(request.getFirstString(), request.getSecondString());
    }


    private void forgotPassword(String username, String email) {
        User user = User.getUserByUsername(username);
        if (user == null || !user.checkEmail(email)) {
            sendError(Error.USER_EMAIL_NOT_EXIST.getError());
        } else {
            user.sendForgotPasswordCode();
            actionCompleted();
        }
    }

}//end ForgotPassWordCommand Class


class NewPasswordCommand extends LoginCommands {
    private static NewPasswordCommand command;

    private NewPasswordCommand() {
        this.name = "new password";
    }


    public static NewPasswordCommand getInstance() {
        if (command != null)
            return command;
        command = new NewPasswordCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)))
            return;

        newPassword(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2));
    }


    private void newPassword(String username, String code, String newPassword) {
        User user = User.getUserByUsername(username);
        if (user == null) {
            sendError(Error.USER_NOT_EXIST.getError());
        } else if (!user.doesCodeSend()) {
            sendError(Error.ERROR.getError());
        } else if (!user.sendCodeIsCorrect(code)) {
            sendError(Error.INCORRECT_CODE.getError());
        } else if (!User.isPasswordValid(newPassword)) {
            sendError(Error.PASSWORD_NOT_VALID.getError());
        } else {
            user.setSendCode("");
            user.setPassword(newPassword);
            user.updateDatabase().update();
        }
    }

}//end NewPasswordCommand Class


class LogoutCommand extends LoginCommands {
    private static LogoutCommand command;

    private LogoutCommand() {
        this.name = "logout";
    }


    public static LogoutCommand getInstance() {
        if (command != null)
            return command;
        command = new LogoutCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        logout();
    }


    private void logout() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
        } else {
            setLoggedUser(null);
            ShoppingCart.setLocalShoppingCart(new ShoppingCart());
            actionCompleted();
        }
    }

}//end LogoutCommand Class
