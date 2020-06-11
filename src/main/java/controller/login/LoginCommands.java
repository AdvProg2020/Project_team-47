package controller.login;

import controller.Command;
import model.ecxeption.Exception;
import model.ecxeption.user.*;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;
import model.user.User;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap());
        checkPrimaryErrors(request);
        register(request.getHashMap());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        HashMap<String, String> registerInformationHashMap = request.getHashMap();

        checkRegisterInfoKey(registerInformationHashMap);
        if (registerInformationHashMap.get("type").equals("manager") && User.isThereManager()) {
            throw new ManagerExistenceException.ManagerExist();
        } else if (!registerInformationHashMap.get("type").equals("manager") && !User.isThereManager()) {
            throw new ManagerExistenceException.ManagerDoesntExist();
        }

        registerInformationIsValid(registerInformationHashMap);
    }


    public void register(HashMap<String, String> registerInformationHashMap) throws RegisterException {
        //this function will register user
        registerUser(registerInformationHashMap, registerInformationHashMap.get("type"));
    }


    private void registerInformationIsValid(HashMap<String, String> userInfo) throws RegisterException, PasswordNotValidException {
        if (!User.isUsernameValid(userInfo.get("username"))) {
            throw new RegisterException.UsernameNotValidException();
        } else if (User.doesUsernameUsed(userInfo.get("username"))) {
            throw new RegisterException.UsernameUsedException();
        } else if (!User.isEmailValid(userInfo.get("email"))) {
            throw new RegisterException.EmailNotValidException();
        } else if (!User.isPhoneValid(userInfo.get("phone-number"))) {
            throw new RegisterException.PhoneNumberNotValidException();
        } else if (User.isThereUserWithEmail(userInfo.get("email"))) {
            throw new RegisterException.EmailUsedException();
        } else if (User.isThereUserWithPhone(userInfo.get("phone-number"))) {
            throw new RegisterException.PhoneNumberUsedException();
        } else if (!User.isPasswordValid(userInfo.get("password"))) {
            throw new PasswordNotValidException();
        }
    }


    private void registerUser(HashMap<String, String> userInformation, String userType) throws RegisterException {
        User newUser = switch (userType) {
            case "customer" -> new Customer(userInformation);
            case "seller" -> new Seller(userInformation);
            case "manager" -> new Manager(userInformation);
            default -> throw new RegisterException("Enter valid type!!");
        };
        newUser.emailVerification();
    }

}//end RegisterCommand Class


class LoginCommand extends LoginCommands {
    private static LoginCommand command;
    private User user;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo, reqInfo.get("username"), reqInfo.get("password"));
        checkPrimaryErrors(request);
        return login(reqInfo.get("username"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        String username = reqInfo.get("username");
        String password = reqInfo.get("password");
        user = User.getUserByUsername(username);
        user.checkPassword(password);
    }


    public ServerMessage login(String username) {
        assert user != null;
        setLoggedUser(user);
        if (user instanceof Customer) {
            ((Customer) user).getShoppingCart().mergingWithLocalCart(ShoppingCart.getLocalShoppingCart());
        }
        return sendAnswer(getLoggedUser().getType());
    }

}//end Login command Class


class ConfirmEmailCommand extends LoginCommands {
    private static ConfirmEmailCommand command;
    private User user;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo);
        containNullField(reqInfo.get("username"), reqInfo.get("password"), reqInfo.get("verification code"));
        return confirmEmail();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws ConfirmException, UserNotExistException, WrongPasswordException {
        HashMap<String, String> reqInfo = getReqInfo(request);
        String username = reqInfo.get("username");
        String password = reqInfo.get("password");
        String verificationCode = reqInfo.get("verification code");

        user = User.getUserInVerificationList(username);
        user.checkPassword(password);
        if (!user.doesCodeSend()) {
            throw new ConfirmException("We didn't send any code to your email!!");
        } else if (!user.sendCodeIsCorrect(verificationCode)) {
            throw new ConfirmException("Incorrect code!!");
        }
    }


    private ServerMessage confirmEmail() {
        user.confirmEmail();
        setLoggedUser(user);
        return actionCompleted();
    }

}//end ConfirmEmailCommand Class


class ForgotPasswordCommand extends LoginCommands {
    private static ForgotPasswordCommand command;
    private User user;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo.get("username"), reqInfo.get("email"));
        checkPrimaryErrors(request);
        return forgotPassword(reqInfo.get("username"), reqInfo.get("email"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        user = User.getUserByUsername(request.getHashMap().get("username"));
        if (user == null || !user.checkEmail(request.getHashMap().get("email"))) {
            throw new UserNotExistException();
        }
    }


    private ServerMessage forgotPassword(String username, String email) throws UserNotExistException {
        user.sendForgotPasswordCode();
        return actionCompleted();
    }

}//end ForgotPassWordCommand Class


class NewPasswordCommand extends LoginCommands {
    private static NewPasswordCommand command;
    private User user;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo.get("username"), reqInfo.get("code"), reqInfo.get("new password"));

        return newPassword(reqInfo.get("new password"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        String username = request.getHashMap().get("username");
        String code = request.getHashMap().get("code");
        String newPassword = request.getHashMap().get("new password");
        user = User.getUserByUsername(username);
        if (!user.doesCodeSend()) {
            throw new Exception("Error!!");
        } else if (!user.sendCodeIsCorrect(code)) {
            throw new ConfirmException("Wrong code!!");
        } else if (!User.isPasswordValid(newPassword)) {
            throw new PasswordNotValidException();
        }
    }


    private ServerMessage newPassword(String newPassword) {
        user.setSendCode("");
        user.setPassword(newPassword);
        user.updateDatabase().update();
        return actionCompleted();
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
    public ServerMessage process(ClientMessage request) {
        return logout();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws NeedLoginException {
        if (getLoggedUser() == null)
            throw new NeedLoginException();
    }


    private ServerMessage logout() {
        setLoggedUser(null);
        ShoppingCart.setLocalShoppingCart(new ShoppingCart());
        return actionCompleted();
    }

}//end LogoutCommand Class
