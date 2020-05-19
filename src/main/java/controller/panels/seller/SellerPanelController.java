package controller.panels.seller;

import controller.Command;
import controller.panels.UserPanelController;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

public class SellerPanelController extends UserPanelController {
    private static SellerPanelController sellerPanelController;
    private ArrayList<Command> commands;

    private SellerPanelController() {
        commands = new ArrayList<>();
        initializeCommonCommands();
        initializeManageOffCommands();
        initializeManageProductCommands();
    }

    public static UserPanelController getInstance() {
        if (sellerPanelController != null)
            return sellerPanelController;
        sellerPanelController = new SellerPanelController();
        return sellerPanelController;
    }

    private void initializeCommonCommands() {
        commands.add(CommonCommands.getAddProductCommand());
        commands.add(CommonCommands.getRemoveProductCommand());
        commands.add(CommonCommands.getShowCategoriesCommand());
        commands.add(CommonCommands.getViewBalanceCommand());
        commands.add(CommonCommands.getViewCompanyInfoCommand());
        commands.add(CommonCommands.getViewSalesHistoryCommand());
    }

    private void initializeManageOffCommands() {
        commands.add(ManageOffCommands.getAddOffCommand());
        commands.add(ManageOffCommands.getEditOffCommand());
        commands.add(ManageOffCommands.getShowOffsCommand());
        commands.add(ManageOffCommands.getViewOffCommand());
    }

    private void initializeManageProductCommands() {
        commands.add(ManageProductCommand.getEditProductCommand());
        commands.add(ManageProductCommand.getShowSellerProductCommand());
        commands.add(ManageProductCommand.getViewBuyersCommand());
        commands.add(ManageProductCommand.getViewProductCommand());
    }

    @Override
    public void processRequest(ClientMessage request) {
        for (Command command : commands) {
            if (canProcess(request.getRequest())) {
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
}//end SellerPanelController class
