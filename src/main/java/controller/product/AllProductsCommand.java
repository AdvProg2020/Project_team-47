package controller.product;

import controller.Command;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.ecxeption.DebugException;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.product.CategoryDoesntExistException;
import model.others.Filter;
import model.others.Product;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class AllProductsCommand extends Command {
    public static ShowProductsWithFilterAndSortCommand getShowProductsWithFilterAndSortCommand() {
        return ShowProductsWithFilterAndSortCommand.getInstance();
    }

    public static CategoryCommand getCategoryCommand() {
        return CategoryCommand.getInstance();
    }

    public static InitializePage getInitializePage() {
        return InitializePage.getInstance();
    }

    protected String sortField() {
        return AllProductsController.getInstance().sortField();
    }

    protected String sortDirection() {
        return AllProductsController.getInstance().sortDirection();
    }

    protected ArrayList<Filter> filters() {
        return AllProductsController.getInstance().filters();
    }

    protected void resetFilters() {
        AllProductsController.getInstance().resetFilters();
    }

    protected void resetSort() {
        AllProductsController.getInstance().resetSort();
    }
}


class ShowProductsWithFilterAndSortCommand extends AllProductsCommand {
    private static ShowProductsWithFilterAndSortCommand command;

    private ShowProductsWithFilterAndSortCommand() {
        this.name = "show products products";
    }

    public static ShowProductsWithFilterAndSortCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowProductsWithFilterAndSortCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) {
        return showProductsWithFilterAndSort();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage showProductsWithFilterAndSort() {
        return sendAnswer(Product.getProductsFiltered(sortField(), sortDirection(), filters()), "product");
    }
}

class CategoryCommand extends AllProductsCommand {
    private static CategoryCommand command;

    private CategoryCommand() {
        this.name = "(show category info|show sub category info|view sub category of main category)";
    }

    public static CategoryCommand getInstance() {
        if (command != null)
            return command;
        command = new CategoryCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws DebugException, NullFieldException, CategoryDoesntExistException, InvalidSortException {
        containNullField(request.getHashMap().get("main category"), request.getHashMap().get("sub category"));
        return switch (request.getType()) {
            case "show category info" -> viewCategory(request.getHashMap().get("main category"));
            case "show sub category info" -> viewSubCategory(request.getHashMap().get("sub category"));
            case "view sub category of main category" -> viewSubcategories(request.getHashMap().get("sub category"),
                    request.getHashMap().get("sort field"), request.getHashMap().get("sort direction"));
            default -> throw new DebugException();
        };
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewCategory(String categoryName) throws CategoryDoesntExistException {
        MainCategory mainCategory = Category.getMainCategoryByName(categoryName);
        return sendAnswer(mainCategory.categoryInfoForSending());
    }

    private ServerMessage viewSubCategory(String subCategoryName) throws CategoryDoesntExistException {
        SubCategory subCategory = Category.getSubCategoryByName(subCategoryName);
        return sendAnswer(subCategory.categoryInfoForSending());
    }

    private ServerMessage viewSubcategories(String mainCategoryName, String sortField, String sortDirection) throws CategoryDoesntExistException, InvalidSortException {
        if (sortField != null && sortDirection != null) {
            sortDirection = sortDirection.toLowerCase();
            sortField = sortField.toLowerCase();
        }
        MainCategory mainCategory = MainCategory.getMainCategoryByName(mainCategoryName);
        if (!checkSort(sortField, sortDirection, "category")) {
            throw new InvalidSortException();
        } else {
            return sendAnswer(mainCategory.getSubcategoriesInfo(sortField, sortDirection), "category");
        }
    }

}

class InitializePage extends AllProductsCommand {
    private static InitializePage command;

    private InitializePage() {
        this.name = "initialize all product page";
    }

    public static InitializePage getInstance() {
        if (command != null)
            return command;
        command = new InitializePage();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) {
        initializeAllProductPage();
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void initializeAllProductPage() {
        resetFilters();
        resetSort();
    }
}