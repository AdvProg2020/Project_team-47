package controller.panels.manager;

import controller.Command;
import controller.panels.UserPanelController;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

public class ManagerPanelController extends UserPanelController {
    private static ManagerPanelController managerPanelController;
    private ArrayList<Command> commands;

    private ManagerPanelController() {
        commands = new ArrayList<>();
        initializeCodeCommands();
        initializeCategoriesCommands();
        initializeRequestsCommands();
        initializeProductCommands();
        initializeUsersCommands();
    }

    public static UserPanelController getInstance() {
        if (managerPanelController != null)
            return managerPanelController;
        managerPanelController = new ManagerPanelController();
        return managerPanelController;
    }

    private void initializeCodeCommands() {
        commands.add(DiscountCodeCommands.getCreateCodeCommand());
        commands.add(DiscountCodeCommands.getEditCodeCommand());
        commands.add(DiscountCodeCommands.getGiveGiftCommand());
        commands.add(DiscountCodeCommands.getRemoveCodeCommand());
        commands.add(DiscountCodeCommands.getViewCodeCommand());
        commands.add(DiscountCodeCommands.getViewCodesCommand());
    }

    private void initializeCategoriesCommands() {
        commands.add(ManageCategoriesCommand.getAddCategoryCommand());
        commands.add(ManageCategoriesCommand.getEditCategoryCommand());
        commands.add(ManageCategoriesCommand.getRemoveCategoryCommand());
        commands.add(ManageCategoriesCommand.getShowCategoriesCommand());
        commands.add(ManageCategoriesCommand.getAddSubCategoryCommand());
        commands.add(ManageCategoriesCommand.getEditSubCategoryCommand());
        commands.add(ManageCategoriesCommand.getRemoveSubCategoryCommand());
        commands.add(ManageCategoriesCommand.getShowSubCategoriesCommand());
    }

    private void initializeRequestsCommands() {
        commands.add(ManageRequestsCommands.getShowRequestsCommand());
        commands.add(ManageRequestsCommands.getRequestDetailCommand());
        commands.add(ManageRequestsCommands.getAcceptRequestCommand());
        commands.add(ManageRequestsCommands.getDeclineRequestCommand());
    }

    private void initializeProductCommands() {
        commands.add(ManageProductsCommands.getShowAllProductCommand());
        commands.add(ManageProductsCommands.getRemoveProductCommand());
    }

    private void initializeUsersCommands() {
        commands.add(ManageUsersCommands.getCreateManagerCommand());
        commands.add(ManageUsersCommands.getShowUsersCommand());
        commands.add(ManageUsersCommands.getViewUserCommand());
        commands.add(ManageUsersCommands.getDeleteUserCommand());
    }

}//end ManagerPanelController
