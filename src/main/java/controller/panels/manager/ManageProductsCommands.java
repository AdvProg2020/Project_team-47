package controller.panels.manager;

import controller.Command;
import controller.Error;
import model.others.Product;
import model.send.receive.ClientMessage;
import model.user.Manager;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class ManageProductsCommands extends Command {
    public static ShowAllProductsCommand getShowAllProductCommand() {
        return ShowAllProductsCommand.getInstance();
    }

    public static RemoveProductCommand getRemoveProductCommand() {
        return RemoveProductCommand.getInstance();
    }

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Manager)) {
            sendError(Error.NEED_MANGER.getError());
            return false;
        }
        return true;
    }

}


class ShowAllProductsCommand extends ManageProductsCommands {
    private static ShowAllProductsCommand command;

    private ShowAllProductsCommand() {
        this.name = "manage all products";
    }


    public static ShowAllProductsCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowAllProductsCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        manageAllProducts(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void manageAllProducts(String sortField, String sortDirection) {
        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "product")) {
            sendError("Can't sort with this field and direction!!");
            return;
        }

        sendAnswer(Product.getAllProductInfo(sortField, sortDirection), "product");
    }

}//end ShowAllProductsCommand class

class RemoveProductCommand extends ManageProductsCommands {
    private static RemoveProductCommand command;

    private RemoveProductCommand() {
        this.name = "remove product manager";
    }


    public static RemoveProductCommand getInstance() {
        if (command != null)
            return command;
        command = new RemoveProductCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        removeProduct(request.getArrayList().get(0));
    }

    public void removeProduct(String productId) {
        if (!Product.isThereProduct(productId)) {
            sendError("There isn't any product with this id!!");
            return;
        }
        Product.removeProduct(productId);
        actionCompleted();
    }
}//end RemoveProductCommands
