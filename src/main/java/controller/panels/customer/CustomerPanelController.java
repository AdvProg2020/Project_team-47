package controller.panels.customer;

import controller.Command;
import controller.panels.UserPanelController;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;

public class CustomerPanelController extends UserPanelController {
    private static CustomerPanelController customerPanelController;
    private ArrayList<Command> commands;

    private CustomerPanelController() {
        commands = new ArrayList<>();
        initializeCartCommands();
        initializeOtherCommands();
    }

    public static UserPanelController getInstance() {
        if (customerPanelController != null)
            return customerPanelController;
        customerPanelController = new CustomerPanelController();
        return customerPanelController;
    }

    private void initializeOtherCommands() {
        commands.add(OtherCommands.getRateCommand());
        commands.add(OtherCommands.getViewBalanceCommand());
        commands.add(OtherCommands.getViewCodesCommand());
        commands.add(OtherCommands.getViewOrderCommand());
        commands.add(OtherCommands.getViewOrdersCommand());
    }

    private void initializeCartCommands() {
        commands.add(CartCommands.getDecreaseCommand());
        commands.add(CartCommands.getIncreaseCommand());
        commands.add(CartCommands.getTotalPriceCommand());
        commands.add(CartCommands.getViewCartCommand());
    }

    @Override
    public ServerMessage processRequest(ClientMessage request) throws Exception {
        for (Command command : commands)
            if (command.canDoIt(request.getType()))
                return command.process(request);

        throw new CommonException("Can't do this request!!");
    }

}//end CustomerPanelController class
