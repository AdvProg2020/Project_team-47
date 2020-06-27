package controller.panels;


import controller.Command;
import controller.Controller;
import controller.panels.customer.CustomerPanelController;
import controller.panels.manager.ManagerPanelController;
import controller.panels.seller.SellerPanelController;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.user.NeedLoginException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;

public class UserPanelController extends Controller {
    private static UserPanelController userPanelController;
    private ArrayList<UserPanelController> subControllers;

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
    public ServerMessage processRequest(ClientMessage request) throws Exception {
        if (loggedUser == null && !isCartCommand(request.getType()))
            throw new NeedLoginException();

        for (Command command : commands)
            if (command.canDoIt(request.getType()))
                return command.process(request);

        for (Controller controller : subControllers)
            if (controller.canProcess(request.getType()))
                return controller.processRequest(request);

        throw new CommonException("Can't do this request!!");
    }

    private boolean isCartCommand(String type) {
        return type.equals("decrease product in cart") || type.equals("increase product in cart") ||
                type.equals("show products in cart");
    }


    @Override
    public boolean canProcess(String request) {
        for (Command command : commands) {
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
