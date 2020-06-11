package controller.panels.manager;

import controller.Command;
import model.ecxeption.Exception;
import model.ecxeption.filter.InvalidSortException;
import model.others.Product;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class ManageProductsCommands extends Command {
    public static ShowAllProductsCommand getShowAllProductCommand() {
        return ShowAllProductsCommand.getInstance();
    }

    public static RemoveProductCommand getRemoveProductCommand() {
        return RemoveProductCommand.getInstance();
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
    public ServerMessage process(ClientMessage request) throws InvalidSortException {
        return manageAllProducts(request.getHashMap().get("sort field"), request.getHashMap().get("sort direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage manageAllProducts(String sortField, String sortDirection) throws InvalidSortException {
        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "product")) {
            throw new InvalidSortException();
        }
        return sendAnswer(Product.getAllProductInfo(sortField, sortDirection), "product");
    }

}//end ShowAllProductsCommand class

class RemoveProductCommand extends ManageProductsCommands {
    private static RemoveProductCommand command;
    private Product product;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        checkPrimaryErrors(request);
        removeProduct();
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        product = Product.getProductWithId(request.getHashMap().get("id"));
    }

    public void removeProduct() {
        product.removeProduct();
    }
}//end RemoveProductCommands
