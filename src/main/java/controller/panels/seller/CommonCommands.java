package controller.panels.seller;

import controller.Command;
import controller.Error;
import model.category.Category;
import model.others.Product;
import model.send.receive.ClientMessage;
import model.user.Seller;

import java.util.HashMap;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class CommonCommands extends Command {
    public static ViewCompanyInfoCommand getViewCompanyInfoCommand() {
        return ViewCompanyInfoCommand.getInstance();
    }

    public static ViewSalesHistoryCommand getViewSalesHistoryCommand() {
        return ViewSalesHistoryCommand.getInstance();
    }

    public static AddProductCommand getAddProductCommand() {
        return AddProductCommand.getInstance();
    }

    public static RemoveProductCommand getRemoveProductCommand() {
        return RemoveProductCommand.getInstance();
    }

    public static ShowCategoriesCommand getShowCategoriesCommand() {
        return ShowCategoriesCommand.getInstance();
    }

    public static ViewBalanceCommand getViewBalanceCommand() {
        return ViewBalanceCommand.getInstance();
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


class ViewCompanyInfoCommand extends CommonCommands {
    private static ViewCompanyInfoCommand command;

    private ViewCompanyInfoCommand() {
        this.name = "view company information";
    }

    public static ViewCompanyInfoCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewCompanyInfoCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        companyInfo();
    }

    private void companyInfo() {
        Seller seller = (Seller) getLoggedUser();
        sendAnswer(seller.getCompanyName(), seller.getCompanyInfo());
    }

}//end ViewCompanyInfoCommand class

class ViewSalesHistoryCommand extends CommonCommands {
    private static ViewSalesHistoryCommand command;

    private ViewSalesHistoryCommand() {
        this.name = "view sales history";
    }

    public static ViewSalesHistoryCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewSalesHistoryCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        salesHistory(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void salesHistory(String field, String direction) {
        //this function will sending selling log info to the client
        if (field != null && direction != null) {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
        }

        if (checkSort(field, direction, "log")) {
            sendError("Can't sort log with this field or direction!!");
            return;
        }
        sendAnswer(((Seller) getLoggedUser()).getAllSellLogsInfo(field, direction), "log");
    }
}//end ViewSalesHistoryCommand class

class AddProductCommand extends CommonCommands {
    private static AddProductCommand command;

    private AddProductCommand() {
        this.name = "add product";
    }

    public static AddProductCommand getInstance() {
        if (command != null)
            return command;
        command = new AddProductCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstHashMap(), request.getSecondHashMap()))
            return;
        addProduct(request.getFirstHashMap(), request.getSecondHashMap());
    }


    public void addProduct(HashMap<String, String> productInfo, HashMap<String, String> specialProperties) {
        //this function will check product info if it is valid then call addProduct method by seller to creating request

        if (checkAddingProductInformation(productInfo)) {
            return;
        }

        Category category = Category.getSubCategoryByName(productInfo.get("sub-category"));
        if (category == null) {
            category = Category.getMainCategoryByName(productInfo.get("category"));
        }
        assert category != null;
        if (!productPropertiesIsValid(specialProperties, category)) {
            return;
        }

        ((Seller) getLoggedUser()).addProduct(productInfo, specialProperties);
    }


    private boolean productPropertiesIsValid(HashMap<String, String> properties, Category category) {
        //this function will check category properties and
        //if product has all its category's properties it will return true

        for (String specialProperty : category.getSpecialProperties()) {
            if (!properties.containsKey(specialProperty)) {
                sendError("This product with this category should have this property:\n" + specialProperty);
                return false;
            }
        }
        return true;
    }

    private boolean checkAddingProductInformation(HashMap<String, String> productInformationHashMap) {
        //check that if client send all information to creating product

        String[] productKey = {"name", "price", "number-in-stock", "category", "description", "sub-category", "company"};
        for (String key : productKey) {
            if (!productInformationHashMap.containsKey(key)) {
                sendError("Not enough information!!");
                return false;
            }
        }
        if (!Category.isThereMainCategory(productInformationHashMap.get("category"))) {
            sendError("There isn't category with this name!!");
            return false;
        }

        String subCategory = productInformationHashMap.get("sub-category");
        if (!subCategory.isEmpty() && !Category.isThereSubCategory(subCategory)) {
            sendError("There isn't sub category with this name!!");
            return false;
        }

        return checkProductIntegerValue(productInformationHashMap);
    }

    private boolean checkProductIntegerValue(HashMap<String, String> productInfo) {
        try {
            Integer.parseInt(productInfo.get("number-in-stock"));
            Double.parseDouble(productInfo.get("price"));
            return true;
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return false;
        }
    }

}//end AddProductCommand class

class RemoveProductCommand extends CommonCommands {
    private static RemoveProductCommand command;

    private RemoveProductCommand() {
        this.name = "remove product seller";
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
        if (containNullField(request.getFirstString()))
            return;
        removeProduct(request.getFirstString());
    }

    private void removeProduct(String productId) {
        Product product = Product.getProductWithId(productId);
        if (product == null || !((Seller) getLoggedUser()).getAllProducts().contains(product)) {
            sendError("There isn't any product with this id in your selling list!!");
            return;
        }

        ((Seller) getLoggedUser()).removeProduct(product);
        product.removeSellerFromProduct((Seller) getLoggedUser());
    }
}//end RemoveProductCommand class

class ShowCategoriesCommand extends CommonCommands {
    private static ShowCategoriesCommand command;

    private ShowCategoriesCommand() {
        this.name = "show categories seller";
    }

    public static ShowCategoriesCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowCategoriesCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        manageCategories(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void manageCategories(String sortField, String sortDirection) {
        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "category")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection), "category");
    }

}//end ShowCategoriesCommand class

class ViewBalanceCommand extends CommonCommands {
    private static ViewBalanceCommand command;

    private ViewBalanceCommand() {
        this.name = "view balance seller";
    }

    public static ViewBalanceCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewBalanceCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        viewBalance();
    }

    public void viewBalance() {
        double money = ((Seller) getLoggedUser()).getMoney();
        sendAnswer(money);
    }

}
