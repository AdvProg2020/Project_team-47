package controller.panels.seller;

import controller.Command;
import controller.Error;
import model.category.Category;
import model.others.Product;
import model.send.receive.ClientMessage;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Seller)) {
            sendError(Error.NEED_SELLER.getError());
            return false;
        }
        return true;
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        showProducts();
    }

    private void showProducts() {
        //this function will sending seller products info by sorting by seen time

        sendAnswer(((Seller) getLoggedUser()).getAllProductInfo("seen-time", "ascending"), "product");
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        viewProduct(request.getArrayList().get(0));
    }

    private void viewProduct(String id) {
        //this function will send a product info that is in the seller's selling list

        Product product = Product.getProductWithId(id);

        //checking that seller has this product or not
        if (!sellerHasProduct(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        }

        //send product info(if seller has this product)
        sendAnswer(product.getProductInfo());
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        viewBuyers(request.getArrayList().get(0));
    }

    private void viewBuyers(String productId) {
        Product product = Product.getProductWithId(productId);
        if (!sellerHasProduct(product)) {
            sendError("You don't have this product in your selling list!!");
            return;
        }

        sendAnswer(((Seller) getLoggedUser()).getBuyers(product), "user");
    }
}


class EditProductCommand extends ManageProductCommand {
    private static EditProductCommand command;

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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)) || containNullField(request.getObject()))
            return;
        editProduct(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2), request.getObject());
    }

    private void editProduct(String productId, String field, String editType, Object newValue) {
        //this function use to edit product and new value could be a string or HashMap of
        //string to change special properties

        Product product = Product.getProductWithId(productId);

        if (!sellerHasProduct(product)) {
            //check that seller has product and seller can edit product with that field and new value
            sendError("You don't have this product in your selling list!!");
            return;
        } else if (!canEditProduct(field, newValue, editType, product)) {
            //if seller can't edit product with this field and new value the error will send by canEditProduct function
            return;
        }

        ((Seller) getLoggedUser()).editProduct(editType, field, newValue, product);

        actionCompleted();
    }

    private boolean canEditProduct(String field, Object newValue, String editType, Product product) {
        //check that product can be edited with intended field and value

        if (newValue instanceof String) {
            return canEditStringField(field, (String) newValue);
        } else if (newValue instanceof HashMap) {
            try {
                HashMap<String, String> specialProperties = (HashMap<String, String>) newValue;
                if (field.equalsIgnoreCase("special-property") &&
                        canEditProductSpecialProperties(specialProperties, editType, product)) {

                    return true;
                }
            } catch (Exception e) {
                sendError("Wrong properties!!");
            }
        }
        return false;
    }

    private boolean canEditStringField(String field, String newValue) {
        switch (field) {
            case "name":
            case "description":
                if (newValue.isEmpty()) {
                    sendError("This field can't be empty!!");
                    return false;
                }
                return true;

            case "price":
                return isItDouble(newValue);

            case "number-of-product":
                return isItInteger(newValue);

            case "category":
                return canEditMainCategory(newValue);

            case "sub-category":
                return canEditSubCategory(newValue);
        }
        return false;
    }

    private boolean canEditSubCategory(String newValue) {
        if (Category.isThereSubCategory(newValue)) {
            return true;
        }
        sendError("There isn't any subcategory with this name!!");
        return false;
    }

    private boolean canEditMainCategory(String newValue) {
        if (Category.isThereMainCategory(newValue)) {
            return true;
        }
        sendError("There isn't any category with this name!!");
        return false;
    }

    private boolean isItDouble(String newValue) {
        try {
            Double.parseDouble(newValue);
            return true;
        } catch (NumberFormatException e) {
            sendError("Please enter valid number!!");
            return false;
        }
    }

    private boolean isItInteger(String newValue) {
        try {
            Integer.parseInt(newValue);
            return true;
        } catch (NumberFormatException e) {
            sendError("Please enter valid number!!");
            return false;
        }
    }

    private boolean canEditProductSpecialProperties(HashMap<String, String> specialProperties, String editType, Product product) {
        switch (editType) {
            case "append":
                return canAppendSpecialProperties(specialProperties, product);
            case "replace":
                return canReplaceSpecialProperties(specialProperties, product);
            case "change":
                return canChangeSpecialProperties(specialProperties, product);
            case "remove":
                return canRemoveSpecialProperties(specialProperties, product);
            default:
                return false;
        }
    }

    private boolean canChangeSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        //this function check that if special properties entered by use has all product special properties

        for (Map.Entry<String, String> temp : specialProperties.entrySet()) {
            if (!canChangeSpecialProperties(temp, product)) {
                sendError("Can't change this properties!!");
                return false;
            }
        }
        return true;
    }

    private boolean canChangeSpecialProperties(Map.Entry<String, String> changingEntry, Product product) {
        for (Map.Entry<String, String> productEntry : product.getSpecialProperties().entrySet()) {
            if (changingEntry.getKey().equals(productEntry.getKey())) {
                return true;
            }
        }
        return false;
    }

    private boolean canReplaceSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        Category category = product.getSubCategory();
        if (category == null)
            category = product.getMainCategory();
        for (String specialProperty : category.getSpecialProperties()) {
            if (!specialProperties.containsKey(specialProperty)) {
                sendError("Can't replace this properties!!");
                return false;
            }
        }
        return true;
    }

    private boolean canRemoveSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        Category category = product.getSubCategory();
        if (category == null)
            category = product.getMainCategory();
        for (String specialProperty : category.getSpecialProperties()) {
            if (specialProperties.containsKey(specialProperty)) {
                sendError("Can't remove this properties!!");
                return false;
            }
        }
        return true;
    }

    private boolean canAppendSpecialProperties(HashMap<String, String> specialProperties, Product product) {
        for (Map.Entry<String, String> productEntry : product.getSpecialProperties().entrySet()) {
            for (Map.Entry<String, String> temp : specialProperties.entrySet()) {
                if (productEntry.getKey().equals(temp.getKey())) {
                    sendError("Cant't append this properties!!");
                    return false;
                }
            }
        }
        return true;
    }

}
