package controller.product;

import controller.Command;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Filter;
import model.send.receive.ClientMessage;
import model.user.User;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static controller.Controller.*;

public abstract class FilterCommands extends Command {
    public static FilterCommonCommand getFilterCommonCommand() {
        return FilterCommonCommand.getInstance();
    }

    public static AddFilterCommand getAddFilterCommand() {
        return AddFilterCommand.getInstance();
    }

    public static DisableFilterCommand getDisableFilterCommand() {
        return DisableFilterCommand.getInstance();
    }

    protected Category getCategoryInFilter() {
        for (Filter filter : filters()) {
            if (filter.getFilterKey().equals("category")) {
                return Category.getMainCategoryByName(filter.getFirstFilterValue());
            } else if (filter.getFilterKey().equals("sub-category")) {
                return Category.getSubCategoryByName(filter.getFirstFilterValue());
            }
        }
        return null;
    }

    protected ArrayList<Filter> filters() {
        return AllProductsController.getInstance().filters();
    }

    protected void addFilter(Filter filter) {
        AllProductsController.getInstance().addFilter(filter);
    }

    protected void removeFilter(Filter filter) {
        AllProductsController.getInstance().removeFilter(filter);
    }
}

class FilterCommonCommand extends FilterCommands {
    private static FilterCommonCommand command;

    private FilterCommonCommand() {
        this.name = "(show available filters products|current filters products)";
    }

    public static FilterCommonCommand getInstance() {
        if (command != null)
            return command;
        command = new FilterCommonCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        switch (request.getRequest()) {
            case "show available filters products":
                showAvailableFilter();
                break;
            case "current filters products":
                currentFilters();
                break;
        }
    }

    private void showAvailableFilter() {
        ArrayList<String> availableFilters = new ArrayList<>();
        Category category = getCategoryInFilter();
        availableFilters.add("Price");
        availableFilters.add("Score");
        availableFilters.add("Category");
        availableFilters.add("Sub Category");
        availableFilters.add("Name");
        if (category != null) {
            availableFilters.addAll(category.getSpecialProperties());
        }
        sendAnswer(availableFilters, "filter");
    }

    private void currentFilters() {
        ArrayList<String> currentFilters = new ArrayList<>();
        for (Filter filter : filters()) {
            currentFilters.add(filter.toString());
        }
        sendAnswer(currentFilters, "filter");
    }
}

class AddFilterCommand extends FilterCommands {
    private static AddFilterCommand command;

    private AddFilterCommand() {
        this.name = "filter an available filter products";
    }

    public static AddFilterCommand getInstance() {
        if (command != null)
            return command;
        command = new AddFilterCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)) || containNullField(reqInfo.get(3)))
            return;
        filterBy(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2), reqInfo.get(3));
    }

    private void filterBy(String filterType, String filterKey, String firstFilterValue, String secondFilterValue) {
        if (!Pattern.matches("(equality|equation)( special-property)?", filterType)) {
            sendError("Wrong filter type!!");
            return;
        }
        if (doesFilterExist(filterKey)) {
            sendError("There is already a filter with this key!!");
            return;
        } else if (Pattern.matches("(equality|equation)", filterType)) {
            addNonPropertyFilter(filterKey, firstFilterValue, secondFilterValue);
            return;
        }

        Category category = getCategoryInFilter();
        if (filterType.equals("equality special-property")) {
            addPropertiesEqualityFilter(filterKey, firstFilterValue, category);
        } else if (filterType.equals("equation special-property")) {
            addPropertiesEquationFilter(filterKey, firstFilterValue, secondFilterValue, category);
        }
    }

    private void addNonPropertyFilter(String filterKey, String firstFilterValue, String secondFilterValue) {
        switch (filterKey) {
            case "price":
                addPriceFilter(firstFilterValue, secondFilterValue);
                break;
            case "score":
                addScoreFilter(firstFilterValue, secondFilterValue);
                break;
            case "category":
                addCategoryFilter(firstFilterValue);
                break;
            case "sub-category":
                addSubCategoryFilter(firstFilterValue);
                break;
            case "name":
                addNameFilter(firstFilterValue);
                break;
            case "brand":
                addBrandFilter(firstFilterValue);
                break;
            case "availability":
                addAvailabilityFilter(firstFilterValue);
            case "seller":
                addSellerFilter(firstFilterValue);
            default:
                sendError("Wrong key!!");
        }
    }

    private void addSellerFilter(String sellerUsername) {
        if (!User.isThereSeller(sellerUsername)) {
            sendError("There isn't any seller with this username!!");
            return;
        }
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("seller");
        filter.setFirstFilterValue(sellerUsername);
        addFilter(filter);
        actionCompleted();
    }

    private void addAvailabilityFilter(String availability) {
        if (!Pattern.matches("(yes|no)", availability.toLowerCase())) {
            sendError("Wrong value!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("availability");
            filter.setFirstFilterValue(availability);
            addFilter(filter);
            actionCompleted();
        }
    }

    private void addBrandFilter(String brand) {
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("brand");
        filter.setFirstFilterValue(brand);
        addFilter(filter);
        actionCompleted();
    }

    private void addPropertiesEqualityFilter(String filterKey, String firstValue, Category category) {
        if (category == null) {
            sendError("You should filter products by a category or subcategory at the first!!");
        } else if (!category.getSpecialProperties().contains(filterKey)) {
            sendError("The category you choose doesn't have this properties!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality special-property");
            filter.setFilterKey(filterKey);
            filter.setFirstFilterValue(firstValue);
            addFilter(filter);
            actionCompleted();
        }
    }

    private void addPropertiesEquationFilter(String filterKey, String firstValue, String secondValue, Category category) {
        if (category == null) {
            sendError("You should filter products by a category or subcategory at the first!!");
        } else if (!category.getSpecialProperties().contains(filterKey)) {
            sendError("The category you choose doesn't have this property!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equation special-property");
            filter.setFilterKey(filterKey);
            try {
                filter.setFirstDouble(Double.parseDouble(firstValue));
                filter.setSecondDouble(Double.parseDouble(secondValue));
            } catch (NumberFormatException e) {
                sendError("Please enter valid number!!");
            }
            addFilter(filter);
            actionCompleted();
        }
    }

    private boolean hasCategoryFilter() {
        for (Filter filter : filters()) {
            if (filter.getFilterKey().equals("category")) {
                return Category.isThereMainCategory(filter.getFirstFilterValue());
            } else if (filter.getFilterKey().equals("sub-category")) {
                return Category.isThereSubCategory(filter.getFirstFilterValue());
            }
        }
        return false;
    }

    private void addNameFilter(String productName) {
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("name");
        filter.setFirstFilterValue(productName);
        addFilter(filter);
        actionCompleted();
    }

    private void addSubCategoryFilter(String subcategoryName) {
        SubCategory subCategory = Category.getSubCategoryByName(subcategoryName);
        if (subCategory == null) {
            sendError("There isn't any category with this name!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("sub-category");
            filter.setFirstFilterValue(subcategoryName);
            addFilter(filter);
            actionCompleted();
        }
    }

    private void addCategoryFilter(String categoryName) {
        MainCategory mainCategory = MainCategory.getMainCategoryByName(categoryName);
        if (mainCategory == null) {
            sendError("There isn't any category with this name!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("category");
            filter.setFirstFilterValue(categoryName);
            addFilter(filter);
            actionCompleted();
        }
    }

    private void addScoreFilter(String firstValue, String secondValue) {
        try {
            double min = Double.parseDouble(firstValue);
            double max = Double.parseDouble(secondValue);
            if (min < 0 || max < min || max > 5) {
                sendError("Please enter numbers between 0 and 5!!");
                return;
            }
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("score");
            filter.setFirstDouble(min);
            filter.setSecondDouble(max);
            addFilter(filter);
            actionCompleted();
        } catch (NumberFormatException e) {
            sendError("Please enter valid value!!");
        }
    }

    private void addPriceFilter(String firstValue, String secondValue) {
        try {
            double min = Double.parseDouble(firstValue);
            double max = Double.parseDouble(secondValue);
            if (min < 0 || min > max) {
                throw new NumberFormatException();
            }
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("price");
            filter.setFirstDouble(min);
            filter.setSecondDouble(max);
            addFilter(filter);
            actionCompleted();
        } catch (NumberFormatException e) {
            sendError("Please enter valid number!!");
        }
    }

    private boolean doesFilterExist(String filterKey) {
        int flag = 1;
        if (Pattern.matches("(category|sub-category)", filterKey))
            flag = 0;
        for (Filter filter : filters()) {
            if (flag == 0) {
                if (filter.getFilterKey().contains(filterKey))
                    return true;

            } else {
                if (filter.getFilterKey().equals(filterKey))
                    return true;

            }
        }
        return false;
    }
}

class DisableFilterCommand extends FilterCommands {
    private static DisableFilterCommand command;

    private DisableFilterCommand() {
        this.name = "disable a selected filter products";
    }

    public static DisableFilterCommand getInstance() {
        if (command != null)
            return command;
        command = new DisableFilterCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString()))
            return;
        disableFilter(request.getFirstString());
    }

    private void disableFilter(String filterKey) {
        for (Filter filter : filters()) {
            if (filter.getFilterKey().equals(filterKey)) {
                removeFilter(filter);
                actionCompleted();
                return;
            }
        }
        sendError("There isn't any filter with this key!!");
    }
}
