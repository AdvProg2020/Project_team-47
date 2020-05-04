package controller;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Filter;
import model.others.Product;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AllProductController extends Controller {
    private static ArrayList<Filter> filters;
    private static String sortFiled;
    private static String sortDirection;

    static {
        filters = new ArrayList<>();
    }

    public static void viewCategories(String sortFiled, String sortDirection) {
        filters = new ArrayList<>();
        sendAnswer(Category.getAllCategoriesInfo(sortFiled, sortDirection));
    }

    public static void viewSubcategory(String mainCategoryName, String sortFiled,String sortDirection) {
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
        if (!Pattern.matches("(equality|equation)( special-properties)?", filterType)) {
            sendError("Wrong filter type!!");
            return;
        }
        if (doesFilterExist(filterKey)) {
            sendError("There is already a filter with this key!!");
            return;
        } else if (Pattern.matches("(equality|equation)", filterType)) {
            addNonPropertiesFilter(filterKey, firstFilterValue, secondFilterValue);
            return;
        }

        Category category = getCategoryInFilter();
        if (filterType.equals("equality special-properties")) {
            addPropertiesEqualityFilter(filterKey, firstFilterValue, category);
        } else if (filterType.equals("equation special-properties")) {
            addPropertiesEquationFilter(filterKey, firstFilterValue, secondFilterValue, category);
        }

    }

    private static void addNonPropertiesFilter(String filterKey, String firstFilterValue, String secondFilterValue) {
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
        }

    }

    private static void addPropertiesEqualityFilter(String filterKey, String firstValue, Category category) {
        if (category == null) {
            sendError("You should filter products by a category or subcategory at the first!!");
        } else if (!category.getSpecialProperties().contains(filterKey)) {
            sendError("The category you choose doesn't have this properties!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality special-properties");
            filter.setFilterKey(filterKey);
            filter.setFirstFilterValue(firstValue);
            filters.add(filter);
        }

    }

    private static void addPropertiesEquationFilter(String filterKey, String firstValue, String secondValue, Category category) {
        if (category == null) {
            sendError("You should filter products by a category or subcategory at the first!!");
        } else if (!category.getSpecialProperties().contains(filterKey)) {
            sendError("The category you choose doesn't have this properties!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equation special-properties");
            filter.setFilterKey(filterKey);
            try {
                filter.setFirstDouble(Double.parseDouble(firstValue));
                filter.setSecondDouble(Double.parseDouble(secondValue));
            } catch (NumberFormatException e) {
                sendError("Please enter valid number!!");
            }
            filters.add(filter);
        }
    }

    private static Category getCategoryInFilter() {
        if (!hasCategoryFilter()) {
            return null;
        }
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
    }

    private static void addSubCategoryFilter(String subcategoryName) {
        SubCategory mainCategory = Category.getSubCategoryByName(subcategoryName);
        if (mainCategory == null) {
            sendError("There isn't any category with this name!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("sub-category");
            filter.setFirstFilterValue(subcategoryName);
            filters.add(filter);
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
        } catch (NumberFormatException e) {
            sendError("Please enter valid value!!");
        }
    }

    private static void addPriceFilter(String firstValue, String secondValue) {
        try {
            double min = Double.parseDouble(firstValue);
            double max = Double.parseDouble(secondValue);
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("price");
            filter.setFirstDouble(min);
            filter.setSecondDouble(max);
            filters.add(filter);
        } catch (NumberFormatException e) {
            sendError("Please enter valid value!!");
        }
    }

    private static boolean doesFilterExist(String filterKey) {
        for (Filter filter : filters) {
            if (filter.getFilterKey().equals(filterKey)) {
                return true;
            }
        }
        return false;
    }

    public static void disableFilter(String filterKey) {
        for (Filter filter : filters) {
            if (filter.getFilterKey().equals(filterKey)) {
                filters.remove(filter);
                return;
            }
        }
        sendError("There isn't any filter with this key!!");
    }

    public static void showProductsWithFilterAndSort() {
        sendAnswer(Product.getProductsFiltered(sortFiled, sortDirection, filters));
    }


    public static void sort(String sortField, String sortDirection) {
        if (Pattern.matches("(name|score|seen-time|date)", sortField) &&
                Pattern.matches("(ascending|descending)", sortDirection)) {
            AllProductController.sortFiled = sortField;
            AllProductController.sortDirection = sortDirection;
        } else
            sendError("Can't sort with this field and direction!!");
    }

    public static void disableSort() {
        sortDirection = null;
        sortFiled = null;
    }


    public static void showAvailableFilter() {
    }

    public static void currentFilters() {
    }

    public static void currentSort() {
    }

    public static void showAvailableSorts() {
    }
}
