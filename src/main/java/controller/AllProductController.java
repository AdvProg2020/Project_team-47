package controller;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Filter;
import model.others.Product;
import model.user.User;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AllProductController extends Controller {
    private static ArrayList<Filter> filters;
    private static String sortField;
    private static String sortDirection;

    static {
        filters = new ArrayList<>();
    }

    public static void viewCategories(String sortFiled, String sortDirection) {
        filters = new ArrayList<>();
        sendAnswer(Category.getAllCategoriesInfo(sortFiled, sortDirection), "category");
    }

    public static void viewSubcategory(String mainCategoryName, String sortFiled, String sortDirection) {
        MainCategory mainCategory = MainCategory.getMainCategoryByName(mainCategoryName);
        if (mainCategory == null) {
            sendError("There isn't any main category with this name!!");
        } else if (!UserPanelController.checkSort(sortFiled, sortDirection, "category")) {
            sendError("Can't sort with this field and direction!!");
        } else {
            sendAnswer(mainCategory.getSubcategoriesInfo(sortFiled, sortDirection));
        }
    }

    public static void filterBy(String filterType, String filterKey, String firstFilterValue, String secondFilterValue) {
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

    private static void addNonPropertyFilter(String filterKey, String firstFilterValue, String secondFilterValue) {
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
                addAvailability(firstFilterValue);
            case "seller":
                addSellerFilter(firstFilterValue);
            default:
                sendError("Wrong key!!");
        }
    }

    private static void addSellerFilter(String sellerUsername) {
        if (!User.isThereSeller(sellerUsername)) {
            sendError("There isn't any seller with this username!!");
            return;
        }
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("seller");
        filter.setFirstFilterValue(sellerUsername);
        filters.add(filter);
        actionCompleted();
    }

    private static void addAvailability(String availability) {
        if (!Pattern.matches("(yes|no)", availability.toLowerCase())) {
            sendError("Wrong value!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("availability");
            filter.setFirstFilterValue(availability);
            filters.add(filter);
            actionCompleted();
        }
    }

    private static void addBrandFilter(String brand) {
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("brand");
        filter.setFirstFilterValue(brand);
        filters.add(filter);
        actionCompleted();
    }

    private static void addPropertiesEqualityFilter(String filterKey, String firstValue, Category category) {
        if (category == null) {
            sendError("You should filter products by a category or subcategory at the first!!");
        } else if (!category.getSpecialProperties().contains(filterKey)) {
            sendError("The category you choose doesn't have this properties!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality special-property");
            filter.setFilterKey(filterKey);
            filter.setFirstFilterValue(firstValue);
            filters.add(filter);
            actionCompleted();
        }
    }

    private static void addPropertiesEquationFilter(String filterKey, String firstValue, String secondValue, Category category) {
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
            filters.add(filter);
            actionCompleted();
        }
    }

    private static Category getCategoryInFilter() {
        for (Filter filter : filters) {
            if (filter.getFilterKey().equals("category")) {
                return Category.getMainCategoryByName(filter.getFirstFilterValue());
            } else if (filter.getFilterKey().equals("sub-category")) {
                return Category.getSubCategoryByName(filter.getFirstFilterValue());
            }
        }
        return null;
    }

    private static boolean hasCategoryFilter() {
        for (Filter filter : filters) {
            if (filter.getFilterKey().equals("category")) {
                return Category.isThereMainCategory(filter.getFirstFilterValue());
            } else if (filter.getFilterKey().equals("sub-category")) {
                return Category.isThereSubCategory(filter.getFirstFilterValue());
            }
        }
        return false;
    }

    private static void addNameFilter(String productName) {
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("name");
        filter.setFirstFilterValue(productName);
        filters.add(filter);
        actionCompleted();
    }

    private static void addSubCategoryFilter(String subcategoryName) {
        SubCategory subCategory = Category.getSubCategoryByName(subcategoryName);
        if (subCategory == null) {
            sendError("There isn't any category with this name!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("sub-category");
            filter.setFirstFilterValue(subcategoryName);
            filters.add(filter);
            actionCompleted();
        }
    }

    private static void addCategoryFilter(String categoryName) {
        MainCategory mainCategory = MainCategory.getMainCategoryByName(categoryName);
        if (mainCategory == null) {
            sendError("There isn't any category with this name!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("category");
            filter.setFirstFilterValue(categoryName);
            filters.add(filter);
            actionCompleted();
        }
    }

    private static void addScoreFilter(String firstValue, String secondValue) {
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
            filters.add(filter);
            actionCompleted();
        } catch (NumberFormatException e) {
            sendError("Please enter valid value!!");
        }
    }

    private static void addPriceFilter(String firstValue, String secondValue) {
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
            filters.add(filter);
            actionCompleted();
        } catch (NumberFormatException e) {
            sendError("Please enter valid number!!");
        }
    }

    private static boolean doesFilterExist(String filterKey) {
        int flag = 1;
        if (Pattern.matches("(category|sub-category)", filterKey))
            flag = 0;
        for (Filter filter : filters) {
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

    public static void disableFilter(String filterKey) {
        for (Filter filter : filters) {
            if (filter.getFilterKey().equals(filterKey)) {
                filters.remove(filter);
                actionCompleted();
                return;
            }
        }
        sendError("There isn't any filter with this key!!");
    }

    public static void showProductsWithFilterAndSort() {
        sendAnswer(Product.getProductsFiltered(sortField, sortDirection, filters), "product");
    }

    public static void sort(String sortField, String sortDirection) {
        if (Pattern.matches("(name|score|seen-time|price)", sortField) &&
                Pattern.matches("(ascending|descending)", sortDirection)) {
            AllProductController.sortField = sortField;
            AllProductController.sortDirection = sortDirection;
            actionCompleted();
        } else
            sendError("Can't sort with this field and direction!!");
    }

    public static void disableSort() {
        sortDirection = null;
        sortField = null;
        actionCompleted();
    }

    public static void showAvailableFilter() {
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

    public static void currentFilters() {
        ArrayList<String> currentFilters = new ArrayList<>();
        for (Filter filter : filters) {
            currentFilters.add(filter.toString());
        }
        sendAnswer(currentFilters, "filter");
    }

    public static void currentSort() {
        if (sortField == null || sortDirection == null) {
            sendError("You didn't any field to sort offs!!");
        } else
            sendAnswer(sortField, sortDirection);
    }

    public static void showAvailableSorts() {
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("Name");
        sorts.add("Score");
        sorts.add("Seen Time");
        sorts.add("Price");
        sorts.add("Seller");
        sorts.add("Brand");
        sorts.add("Availability");

        sendAnswer(sorts, "sort");
    }
}//end AllProductController class
