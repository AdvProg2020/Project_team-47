package controller.panels.seller;

import controller.Command;
import model.category.Category;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.EmptyFieldException;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.common.NumberException;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.others.SpecialProperty;
import model.others.request.EditProductRequest;
import model.others.request.Request;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Seller;

import static controller.Controller.*;

public abstract class ManageProductCommand extends Command {
    protected static boolean sellerHasProduct(Product product) {
        return (product != null && ((Seller) getLoggedUser()).hasProduct(product));
    }

    public static ShowSellerProductCommand getShowSellerProductCommand() {
        return ShowSellerProductCommand.getInstance();
    }

    public static ViewProductCommand getViewProductCommand() {
        return ViewProductCommand.getInstance();
    }

    public static ViewBuyersCommand getViewBuyersCommand() {
        return ViewBuyersCommand.getInstance();
    }

    public static EditProductCommand getEditProductCommand() {
        return EditProductCommand.getInstance();
    }
}


class ShowSellerProductCommand extends ManageProductCommand {
    private static ShowSellerProductCommand command;

    private ShowSellerProductCommand() {
        this.name = "manage products seller";
    }

    public static ShowSellerProductCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowSellerProductCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) {
        return showProducts();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage showProducts() {
        //this function will sending seller products info by sorting by seen time

        return sendAnswer(((Seller) getLoggedUser()).getAllProductInfo("seen-time", "ascending"), "product");
    }
}


class ViewProductCommand extends ManageProductCommand {
    private static ViewProductCommand command;

    private ViewProductCommand() {
        this.name = "view product seller";
    }

    public static ViewProductCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewProductCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, ProductDoesntExistException, CommonException {
        containNullField(request.getHashMap().get("product id"));
        return viewProduct(request.getHashMap().get("product id"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewProduct(String id) throws ProductDoesntExistException, CommonException {
        //this function will send a product info that is in the seller's selling list

        Product product = Product.getProductWithId(id);

        //checking that seller has this product or not
        if (!sellerHasProduct(product)) {
            throw new CommonException("You don't have this product!!");
        }

        //send product info(if seller has this product)
        return sendAnswer(product.getProductInfo());
    }
}


class ViewBuyersCommand extends ManageProductCommand {
    private static ViewBuyersCommand command;

    private ViewBuyersCommand() {
        this.name = "view product buyers";
    }

    public static ViewBuyersCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewBuyersCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException,
            ProductDoesntExistException, CommonException {
        containNullField(request.getHashMap().get("product id"));
        return viewBuyers(request.getHashMap().get("product id"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewBuyers(String productId) throws ProductDoesntExistException, CommonException {
        Product product = Product.getProductWithId(productId);
        if (!sellerHasProduct(product)) throw new CommonException("You don't have this product!!");

        return sendAnswer(((Seller) getLoggedUser()).getBuyers(product), "user");
    }
}


class EditProductCommand extends ManageProductCommand {
    private static EditProductCommand command;
    private Product product;


    private EditProductCommand() {
        this.name = "edit product";
    }

    public static EditProductCommand getInstance() {
        if (command != null)
            return command;
        command = new EditProductCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("product id"), request.getHashMap().get("field"), request.getProperty());
        containNullField(request.getHashMap().get("change type"), request.getHashMap().get("new value"));
        checkPrimaryErrors(request);
        String field = request.getHashMap().get("field");
        editProduct(field, request.getHashMap().get("new value"),
                request.getHashMap().get("change type"), request);
        return actionCompleted();
    }
/*

    private void editProperty(String changeType, SpecialProperty property) throws CommonException {
        if (changeType.equals("remove")) {
            product.getSpecialProperties().remove(property);
        } else {
            if(product.getSpecialProperties().contains(property))
                changeProperty(property);
            else
                product.getSpecialProperties().add(property);
        }
    }

    private void changeProperty(SpecialProperty property) throws CommonException {
        int i = product.getSpecialProperties().indexOf(property);
        if(!property.getType().equals(product.getSpecialProperties().get(i).getType()))
            throw new CommonException("You can't change this property to this value!!");
        SpecialProperty lastProperty = product.getSpecialProperties().get(i);
        lastProperty.setNumericValue(property.getNumericValue());
        lastProperty.setValue(property.getValue());
    }
*/

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        product = Product.getProductWithId(request.getHashMap().get("product id"));

        //check that seller has product and seller can edit product with that field and new value
        if (!sellerHasProduct(product)) throw new CommonException("You don't have this product!!");
        checkErrors(request.getHashMap().get("new value"), request.getHashMap().get("field"));
        if (request.getHashMap().get("field").equals("property"))
            canEditProperty(request.getProperty(), request.getHashMap().get("change type"));
    }

    private void canEditProperty(SpecialProperty property, String changeType) throws CommonException {
        if (!property.isItValid()) throw new CommonException("Invalid property!!");
        if ("remove".equals(changeType)) {
            if (product.getCategory().getSpecialProperties().contains(property))
                throw new CommonException("Can't remove this property!!");
        } else {
            int i = product.getSpecialProperties().indexOf(property);
            if (!property.getType().equals(product.getSpecialProperties().get(i).getType()))
                throw new CommonException("You can't change this property to this value!!");
        }
    }

    private void checkErrors(String newValue, String field) throws EmptyFieldException, NumberException,
            CategoryDoesntExistException {
        switch (field) {
            case "name", "description" -> {
                if (newValue.isEmpty()) throw new EmptyFieldException(field);
            }
            case "price" -> {
                try {
                    Double.parseDouble(newValue);
                } catch (NumberFormatException e) {
                    throw new NumberException();
                }
            }

            case "number-of-product" -> {
                try {
                    Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    throw new NumberException();
                }
            }

            case "category" -> canEditMainCategory(newValue);

            case "sub-category" -> canEditSubCategory(newValue);
        }
    }

    private void editProduct(String field, String newValue, String changeType, ClientMessage req) {
        //this function will create a new request for this seller to editing product with data given to this function
        Request request = new Request();
        request.setRequestSender(getLoggedUser());
        request.setType("edit-product");


        EditProductRequest editProductRequest = new EditProductRequest(field, newValue, changeType, req.getProperty());
        editProductRequest.setField(field);
        editProductRequest.setProductId(product.getId());
        editProductRequest.setSeller(getLoggedUser().getUsername());

        request.setMainRequest(editProductRequest);
        request.addToDatabase();

        product.setStatus("IN_EDITING_QUEUE");
    }

    private void canEditSubCategory(String newValue) throws CategoryDoesntExistException {
        Category.getSubCategoryByName(newValue);
    }

    private void canEditMainCategory(String newValue) throws CategoryDoesntExistException {
        Category.getMainCategoryByName(newValue);
    }
}
