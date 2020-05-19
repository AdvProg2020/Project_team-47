package controller.panels;


import controller.Controller;
import controller.panels.customer.CustomerPanelController;
import controller.panels.manager.ManagerPanelController;
import controller.panels.seller.SellerPanelController;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

public class UserPanelController extends Controller {
    private static UserPanelController userPanelController;
    private ArrayList<UserPanelController> subControllers;
    private ArrayList<UserPanelCommands> commands;

    protected UserPanelController() {
        commands = new ArrayList<>();
        commands.add(UserPanelCommands.getViewPersonalInfoCommand());
        commands.add(UserPanelCommands.getEditFieldCommand());
    }

    public static Controller getInstance() {
        if (userPanelController != null)
            return userPanelController;
        userPanelController = new UserPanelController();
        userPanelController.subControllers = new ArrayList<>();
        userPanelController.subControllers.add(SellerPanelController.getInstance());
        userPanelController.subControllers.add(CustomerPanelController.getInstance());
        userPanelController.subControllers.add(ManagerPanelController.getInstance());
        return userPanelController;
    }


    @Override
    public void processRequest(ClientMessage request) {
        for (UserPanelCommands command : commands) {
            if (command.canDoIt(request.getRequest())) {
                command.process(request);
                return;
            }
        }
        for (UserPanelController subController : subControllers) {
            if (subController.canProcess(request.getRequest())) {
                subController.processRequest(request);
                return;
            }
        }
    }


    @Override
    public boolean canProcess(String request) {
        for (UserPanelCommands command : commands) {
            if (command.canDoIt(request))
                return true;
        }
        for (UserPanelController subController : subControllers) {
            if (subController.canProcess(request))
                return true;
        }
        return false;
    }

}//end UserPanelController class
