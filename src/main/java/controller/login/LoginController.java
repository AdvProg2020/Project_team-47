package controller.login;

import controller.Controller;

import java.util.ArrayList;

public class LoginController extends Controller {
    private static LoginController loginController;


    private LoginController() {
        commands = new ArrayList<>();
        commands.add(LoginCommands.getConfirmEmailCommand());
        commands.add(LoginCommands.getForgotPasswordCommand());
        commands.add(LoginCommands.getLoginCommand());
        commands.add(LoginCommands.getLogoutCommand());
        commands.add(LoginCommands.getRegisterCommand());
        commands.add(LoginCommands.getNewPasswordCommand());
    }


    public static Controller getInstance() {
        if (loginController != null)
            return loginController;
        loginController = new LoginController();
        return loginController;
    }

}//end LoginController class
