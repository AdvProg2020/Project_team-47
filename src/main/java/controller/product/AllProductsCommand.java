package controller.product;

import controller.Command;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Filter;
import model.others.Product;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

import static controller.Controller.*;
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
    public void process(ClientMessage request) {
        showProductsWithFilterAndSort();
    }

    private void showProductsWithFilterAndSort() {
        sendAnswer(Product.getProductsFiltered(sortField(), sortDirection(), filters()), "product");
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
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString()))
            return;
        switch (request.getRequest()) {
            case "show category info":
                viewCategory(request.getFirstString());
                break;
            case "show sub category info":
                viewSubCategory(request.getFirstString());
                break;
            case "view sub category of main category":
                viewSubcategories(request.getFirstString(), request.getArrayList().get(0), request.getArrayList().get(1));
                break;
        }
    }

    private void viewCategory(String categoryName) {
        MainCategory mainCategory = Category.getMainCategoryByName(categoryName);
        if (mainCategory == null) {
            sendError("There isn't any main category with this name!!");
        } else
            sendAnswer(mainCategory.categoryInfoForSending());
    }

    private void viewSubCategory(String subCategoryName) {
        SubCategory mainCategory = Category.getSubCategoryByName(subCategoryName);
        if (mainCategory == null) {
            sendError("There isn't any sub category with this name!!");
        } else
            sendAnswer(mainCategory.categoryInfoForSending());
    }

    private void viewSubcategories(String mainCategoryName, String sortField, String sortDirection) {
        if (sortField != null && sortDirection != null) {
            sortDirection = sortDirection.toLowerCase();
            sortField = sortField.toLowerCase();
        }
        MainCategory mainCategory = MainCategory.getMainCategoryByName(mainCategoryName);
        if (mainCategory == null) {
            sendError("There isn't any main category with this name!!");
        } else if (!checkSort(sortField, sortDirection, "category")) {
            sendError("Can't sort with this field and direction!!");
        } else {
            sendAnswer(mainCategory.getSubcategoriesInfo(sortField, sortDirection), "category");
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
    public void process(ClientMessage request) {
        initializeAllProductPage();
    }

    private void initializeAllProductPage() {
        resetFilters();
        resetSort();
        actionCompleted();
    }
}