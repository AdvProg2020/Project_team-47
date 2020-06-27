package controller.panels.seller;

import controller.Command;
import controller.panels.UserPanelController;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.user.UserTypeException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Seller;

import java.util.ArrayList;

public class SellerPanelController extends UserPanelController {
    private static SellerPanelController sellerPanelController;
    private final ArrayList<Command> commands;

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
//        commands.add(CommonCommands.getShowCategoriesCommand());
        commands.add(CommonCommands.getViewBalanceCommand());
        commands.add(CommonCommands.getViewCompanyInfoCommand());
        commands.add(CommonCommands.getViewSalesHistoryCommand());
        commands.add(CommonCommands.getAddSellerCommand());
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
    public boolean canProcess(String request) {
        for (Command command : commands) {
            if (command.canDoIt(request))
                return true;
        }
        return false;
    }

    @Override
    public ServerMessage processRequest(ClientMessage request) throws Exception {
        if (!(loggedUser instanceof Seller))
            throw new UserTypeException.NeedSellerException();

        for (Command command : commands)
            if (command.canDoIt(request.getType()))
                return command.process(request);

        throw new CommonException("Can't do this request!!");
    }
}//end SellerPanelController class
