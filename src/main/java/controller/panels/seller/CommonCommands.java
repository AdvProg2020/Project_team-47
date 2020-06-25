package controller.panels.seller;

import controller.Command;
import model.category.Category;
import model.ecxeption.Exception;
import model.ecxeption.common.NotEnoughInformation;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.common.NumberException;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.others.SpecialProperty;
import model.others.request.AddProductRequest;
import model.others.request.Request;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Seller;

import java.util.ArrayList;
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

/*
    public static ShowCategoriesCommand getShowCategoriesCommand() {
        return ShowCategoriesCommand.getInstance();
    }
*/

    public static ViewBalanceCommand getViewBalanceCommand() {
        return ViewBalanceCommand.getInstance();
    }

    public static Command getAddSellerCommand() {
        return AddToSeller.getInstance();
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
    public ServerMessage process(ClientMessage request) {
        return companyInfo();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage companyInfo() {
        Seller seller = (Seller) getLoggedUser();
        return sendAnswer(seller.getCompanyName(), seller.getCompanyInfo());
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
    public ServerMessage process(ClientMessage request) throws InvalidSortException {
        return salesHistory(request.getHashMap().get("field"), request.getHashMap().get("direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage salesHistory(String field, String direction) throws InvalidSortException {
        //this function will sending selling log info to the client
        if (field != null && direction != null) {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
        }

        if (!checkSort(field, direction, "log")) {
            throw new InvalidSortException();
        }
        return sendAnswer(((Seller) getLoggedUser()).getAllSellLogsInfo(field, direction), "log");
    }
}//end ViewSalesHistoryCommand class

class AddProductCommand extends CommonCommands {
    private static AddProductCommand command;
    private Category category;

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
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException,
            NumberException, NotEnoughInformation {
        containNullField(request.getProperties(),request.getFile(),request.getFileExtension());
        addProduct(request.getHashMap(), request);
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }


    public void addProduct(HashMap<String, String> productInfo, ClientMessage request) throws
            CategoryDoesntExistException, NumberException, NotEnoughInformation {
        //this function will check product info if it is valid then call addProduct method by seller to creating request
        addingInformationIsValid(productInfo);
        correctProperties(request.getProperties());
        createRequest(productInfo, request);
    }

    private void createRequest(HashMap<String, String> productInfo, ClientMessage clientMessage) {
        //this function will create a request to adding product to intended seller
        Request request = new Request();
        request.setRequestSender(getLoggedUser());
        request.setType("add-product");

        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setSellerUsername(getLoggedUser().getUsername());
        addProductRequest.setCategoryName(productInfo.get("category"));
        addProductRequest.setSubCategoryName(productInfo.get("sub-category"));
        addProductRequest.setCompany(productInfo.get("company"));
        addProductRequest.setDescription(productInfo.get("description"));
        addProductRequest.setName(productInfo.get("name"));
        addProductRequest.setNumberInStock(Integer.parseInt(productInfo.get("number-in-stock")));
        addProductRequest.setPrice(Double.parseDouble(productInfo.get("price")));
        addProductRequest.setSpecialProperties(clientMessage.getProperties());
        addProductRequest.setFile(clientMessage.getFile());
        addProductRequest.setFileExtension(clientMessage.getFileExtension());

        request.setMainRequest(addProductRequest);
        request.addToDatabase();
    }


    private void correctProperties(ArrayList<SpecialProperty> properties) {
        //this function will check category properties and
        //if product has all its category's properties it will return true
        for (SpecialProperty property : category.getSpecialProperties()) {
            if (!properties.contains(property)) {
                properties.add(property);
            } else {
                int i = properties.indexOf(property);
                properties.get(i).confirmProperty(property);
            }
        }
    }

    private void addingInformationIsValid(HashMap<String, String> productInfo) throws NotEnoughInformation,
            CategoryDoesntExistException, NumberException {
        //check that if client send all information to creating product

        String[] productKey = {"name", "price", "number-in-stock", "category", "description", "sub-category", "company"};
        for (String key : productKey) if (!productInfo.containsKey(key)) throw new NotEnoughInformation();

        category = Category.getMainCategoryByName(productInfo.get("category"));

        String subCategory = productInfo.get("sub-category");
        if (!subCategory.isEmpty() && !Category.isThereSubCategory(subCategory))
            category = Category.getSubCategoryByName(subCategory);

        checkProductIntegerValue(productInfo);
    }

    private void checkProductIntegerValue(HashMap<String, String> productInfo) throws NumberException {
        try {
            Integer.parseInt(productInfo.get("number-in-stock"));
            Double.parseDouble(productInfo.get("price"));
        } catch (NumberFormatException e) {
            throw new NumberException("Please enter valid number!!");
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, ProductDoesntExistException {
        containNullField(request.getHashMap().get("product id"));
        removeProduct(request.getHashMap().get("product id"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void removeProduct(String productId) throws ProductDoesntExistException {
        Product product = Product.getProductWithId(productId);
        ((Seller) getLoggedUser()).removeProduct(product);
        product.removeSellerFromProduct((Seller) getLoggedUser());
    }
}//end RemoveProductCommand class

/*
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
*/

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
    public ServerMessage process(ClientMessage request) {
        return viewBalance();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    public ServerMessage viewBalance() {
        double money = ((Seller) getLoggedUser()).getMoney();
        return sendAnswer(money);
    }

}

class AddToSeller extends CommonCommands {
    private static AddToSeller command;

    private AddToSeller() {
        this.name = "add to seller";
    }

    public static AddToSeller getInstance() {
        if(command == null)
            command = new AddToSeller();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("product id"), request.getHashMap().get("number in stock"),
                request.getHashMap().get("price"));
        addToSeller(request.getHashMap().get("product id"), request.getHashMap().get("number in stock"),
                request.getHashMap().get("price"));
        return actionCompleted();
    }

    private void addToSeller(String productId, String numberInStockString, String priceString) throws ProductDoesntExistException, NumberException {
        Product product = Product.getProductWithId(productId);
        double price;
        int numberInStock;
        try {
            price = Double.parseDouble(priceString);
            numberInStock = Integer.parseInt(numberInStockString);
        } catch (NumberFormatException e) {
            throw new NumberException();
        }
        product.addSeller((Seller) getLoggedUser(), numberInStock, price);

        ((Seller) getLoggedUser()).addProduct(product);
        product.updateDatabase();
        getLoggedUser().updateDatabase().update();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }
}
