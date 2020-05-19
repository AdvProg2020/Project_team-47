package controller.login;

import controller.Controller;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

public class LoginController extends Controller {
    private static LoginController loginController;
    private ArrayList<LoginCommands> commands;


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


    @Override
    public void processRequest(ClientMessage request) {
        for (LoginCommands command : commands) {
            if (command.canDoIt(request.getRequest())) {
                command.process(request);
                return;
            }
        }
    }


    @Override
    public boolean canProcess(String request) {
        for (LoginCommands command : commands) {
            if (command.canDoIt(request))
                return true;
        }
        return false;
    }

}//end LoginController class
