package controller.product;

import controller.Command;
import model.category.Category;
import model.category.MainCategory;
import model.ecxeption.DebugException;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.common.NumberException;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.others.ClientFilter;
import model.others.Filter;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;

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
                try {
                    return Category.getMainCategoryByName(filter.getFirstFilterValue());
                } catch (CategoryDoesntExistException e) {
                    e.printStackTrace();
                }
            } else if (filter.getFilterKey().equals("sub-category")) {
                try {
                    return Category.getSubCategoryByName(filter.getFirstFilterValue());
                } catch (CategoryDoesntExistException e) {
                    e.printStackTrace();
                }
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
    public ServerMessage process(ClientMessage request) throws DebugException {
        return switch (request.getType()) {
            case "show available filters products" -> showAvailableFilter();
            case "current filters products" -> currentFilters();
            default -> throw new DebugException();
        };
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage showAvailableFilter() {
        ArrayList<ClientFilter> availableFilters = new ArrayList<>();
        Category category = getCategoryInFilter();
        if (category != null) {
            for (SpecialProperty property : category.getSpecialProperties()) {
                availableFilters.add(new ClientFilter(property.getKey(), property.getType(),
                        property.getUnit()));
            }
        }
        return sendAnswer(availableFilters, "filter");
    }

    private ServerMessage currentFilters() {
        return null;
//        ArrayList<String> currentFilters = new ArrayList<>();
//        for (Filter filter : filters()) {
//            currentFilters.add(filter.toString());
//        }
//        sendAnswer(currentFilters, "filter");
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, CategoryDoesntExistException,
            DebugException, UserNotExistException, NumberException {
        HashMap<String, String> req = getReqInfo(request);
        containNullField(req.get("filter key"), req.get("first filter value"), req.get("second filter value"));
        filterBy(req.get("filter key"), req.get("first filter value"), req.get("second filter value"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void filterBy(String filterKey, String firstFilterValue, String secondFilterValue) throws NumberException, DebugException, CategoryDoesntExistException, UserNotExistException {
        deleteLastFilter(filterKey);
        int flag = 1;
        switch (filterKey) {
            case "price" -> addPriceFilter(firstFilterValue, secondFilterValue);
            case "score" -> addScoreFilter(firstFilterValue, secondFilterValue);
            case "brand" -> addBrandFilter(firstFilterValue);
            case "name" -> addNameFilter(firstFilterValue);
            case "seller" -> addSellerFilter(firstFilterValue);
            case "category" -> addCategoryFilter(firstFilterValue);
            case "sub-category" -> addSubCategoryFilter(firstFilterValue);
            case "availability" -> addAvailabilityFilter(firstFilterValue);
            default -> flag = 0;
        }
        if (flag == 1)
            return;
        Category category = getCategoryInFilter();
        if (category == null)
            throw new DebugException();

        addPropertyFilter(category, filterKey, firstFilterValue, secondFilterValue);
    }

    private void addPropertyFilter(Category category, String filterKey, String firstFilterValue, String secondFilterValue)
            throws NumberException {

        SpecialProperty property = null;
        for (SpecialProperty iterator : category.getSpecialProperties()) {
            if (iterator.getKey().equalsIgnoreCase(filterKey)) {
                property = iterator;
                break;
            }
        }
        assert property != null;
        Filter filter = new Filter();
        filter.setFilterKey(filterKey);
        if (property.getType().equals("text")) {
            filter.setType("equality");
            filter.setFirstFilterValue(firstFilterValue);
        } else {
            filter.setType("equation");
            try {
                filter.setFirstDouble(Double.parseDouble(firstFilterValue));
                filter.setSecondDouble(Double.parseDouble(secondFilterValue));
            } catch (NumberFormatException e) {
                throw new NumberException();
            }
        }

        addFilter(filter);
    }

    private void addSellerFilter(String sellerUsername) throws UserNotExistException {
        if (!User.isThereSeller(sellerUsername))
            throw new UserNotExistException("There isn't any seller with this username!!");

        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("seller");
        filter.setFirstFilterValue(sellerUsername);
        addFilter(filter);
    }

    private void addAvailabilityFilter(String availability) throws DebugException {
        if (!Pattern.matches("(yes|no)", availability.toLowerCase())) {
            throw new DebugException();
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("availability");
            filter.setFirstFilterValue(availability);
            addFilter(filter);
        }
    }

    private void addBrandFilter(String brand) {
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("brand");
        filter.setFirstFilterValue(brand);
        addFilter(filter);
    }

    private void addNameFilter(String productName) {
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("name");
        filter.setFirstFilterValue(productName);
        addFilter(filter);
    }

    private void addSubCategoryFilter(String subcategoryName) throws CategoryDoesntExistException {
        Category.getSubCategoryByName(subcategoryName);
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("sub-category");
        filter.setFirstFilterValue(subcategoryName);
        addFilter(filter);
    }

    private void addCategoryFilter(String categoryName) throws CategoryDoesntExistException {
        MainCategory.getMainCategoryByName(categoryName);
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("category");
        filter.setFirstFilterValue(categoryName);
        addFilter(filter);
    }

    private void addScoreFilter(String firstValue, String secondValue) throws NumberException {
        try {
            double min = Double.parseDouble(firstValue);
            double max = Double.parseDouble(secondValue);
            if (min < 0 || max < min || max > 5) throw new NumberException();
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("score");
            filter.setFirstDouble(min);
            filter.setSecondDouble(max);
            addFilter(filter);
        } catch (NumberFormatException e) {
            throw new NumberException();
        }
    }

    private void addPriceFilter(String firstValue, String secondValue) throws NumberException {
        try {
            double min = Double.parseDouble(firstValue);
            double max = Double.parseDouble(secondValue);
            if (min < 0 || min > max) throw new NumberException();
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("price");
            filter.setFirstDouble(min);
            filter.setSecondDouble(max);
            addFilter(filter);
            actionCompleted();
        } catch (NumberFormatException e) {
            throw new NumberException();
        }
    }

    private void deleteLastFilter(String filterKey) {
        if (Pattern.matches("(category|sub-category)", filterKey))
            disableCategoriesFilter();
        else filters().removeIf(filter -> filter.getFilterKey().equals(filterKey));

    }

    private void disableCategoriesFilter() {
        filters().removeIf(filter -> !Pattern.matches("(price|score|brand|name|seller|availability)", filter.getFilterKey()));
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, DebugException {
        containNullField(request.getHashMap().get("filter key"));
        disableFilter(request.getHashMap().get("filter key"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void disableFilter(String filterKey) throws DebugException {
        filters().removeIf(filter -> filter.getFilterKey().equalsIgnoreCase(filterKey));
    }
}
