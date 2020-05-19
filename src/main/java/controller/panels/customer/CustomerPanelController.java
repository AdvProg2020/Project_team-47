package controller.panels.customer;

import controller.Command;
import controller.panels.UserPanelController;
import model.send.receive.ClientMessage;

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
    public void processRequest(ClientMessage request) {
        for (Command command : commands) {
            if (command.canDoIt(request.getRequest())) {
                command.process(request);
                return;
            }
        }
    }


    @Override
    public boolean canProcess(String request) {
        for (Command command : commands) {
            if (command.canDoIt(request))
                return true;
        }
        return false;
    }

}//end CustomerPanelController class
