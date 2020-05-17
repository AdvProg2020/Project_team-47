package controller;

import model.discount.Off;
import model.others.Filter;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class OffController extends Controller {
    private static ArrayList<Filter> filters;
    private static String sortField;
    private static String sortDirection;
    private static Off off;

    static {
        filters = new ArrayList<>();
    }

    public static void offs() {
        filters = new ArrayList<>();
        sendAnswer(Off.getAllOffsInfo(), "off");
    }

    public static void voidShowOffs() {
        sendAnswer(Off.getAllProductsInOffsInfo(sortField, sortDirection, filters), "off");
    }

    private static void addFilter(String filterKey, String firstFilterValue, String secondFilterValue) {
        if (isFilterExist(filterKey)) {
            sendError("You are already using this filter!!");
            return;
        }

        switch (filterKey) {
            case "time":
                if (!Controller.isDateFormatValid(firstFilterValue) || !Controller.isDateFormatValid(secondFilterValue)) {
                    sendError("Please enter valid date!!");
                } else
                    createFilterForTime(firstFilterValue, secondFilterValue);
                break;

            case "percent":
                try {
                    int min = Integer.parseInt(firstFilterValue);
                    int max = Integer.parseInt(secondFilterValue);
                    createFilterForPercent(min, max);
                } catch (NumberFormatException e) {
                    sendError("Please enter valid number!!");
                }
                break;

            case "seller":
                createFilterForSeller(firstFilterValue);
                break;

            case "off-status":
                createFilterForOffStatus(firstFilterValue);
                break;
            default:
                sendError("Wrong key for filtering!!");
        }
    }

    private static void createFilterForOffStatus(String status) {
        switch (status) {
            case "in-edit-queue":
            case "started":
            case "finished":
            case "accepted-by-manager":
                break;
            default:
                sendError("There is no such status!!");
                return;
        }
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("status");
        filter.setFirstFilterValue(status);
        filters.add(filter);
        actionCompleted();
    }

    private static void createFilterForSeller(String sellerUsername) {
        if (!Seller.isThereSeller(sellerUsername)) {
            sendError("There isn't any seller with this username");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("seller");
            filter.setFirstFilterValue(sellerUsername);
            filters.add(filter);
        }
    }

    private static void createFilterForPercent(int min, int max) {
        if (min < 0 || max <= min || max > 100) {
            sendError("Please enter valid percent!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("percent");
            filter.setFirstInt(min);
            filter.setSecondInt(max);
            filters.add(filter);
            actionCompleted();
        }
    }

    private static void createFilterForTime(String startDate, String finishDate) {
        Date start = Controller.getDateWithString(startDate);
        Date finish = Controller.getDateWithString(finishDate);
        if (start.after(finish)) {
            sendError("Please enter valid date!!");
            return;
        }
        Filter filter = new Filter();
        filter.setType("equation");
        filter.setFilterKey("time");
        filter.setFirstFilterValue(startDate);
        filter.setSecondFilterValue(finishDate);
        filters.add(filter);
        actionCompleted();
    }

    private static boolean isFilterExist(String key) {
        //check that if there is a filter with given key
        switch (key) {
            case "time":
            case "percent":
            case "seller":
            case "off-status":
                for (Filter filter : filters) {
                    if (filter.getFilterKey().equals(key)) {
                        return true;
                    }
                }
                return false;
            default:
                return true;
        }
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

    public static void sort(String sortField, String sortDirection) {
        if (Pattern.matches("(percent|start-time|finish-time)", sortField) &&
                Pattern.matches("(ascending|descending)", sortDirection)) {
            OffController.sortField = sortField;
            OffController.sortDirection = sortDirection;
            actionCompleted();
        } else
            sendError("Can't sort with this field and direction!!");
    }

    public static void disableSort() {
        OffController.sortField = null;
        OffController.sortDirection = null;
        actionCompleted();
    }

    public static void currentSort() {
        if (sortField == null || sortDirection == null) {
            sendError("You didn't any field to sort offs!!");
        } else
            sendAnswer(sortField, sortDirection);
    }

    public static void showAvailableSorts() {
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("Percent");
        sorts.add("Starting Time");
        sorts.add("Finish Time");

        sendAnswer(sorts, "sort");
    }

    public static void showAvailableFilters() {
        ArrayList<String> filters = new ArrayList<>();
        filters.add("Percent");
        filters.add("Start and Finish Time");
        filters.add("Seller Username");
        filters.add("Off status(IN_EDIT_QUEUE | EDIT_ACCEPTED | EDIT_DECLINED | CONFIRMED) ");

        sendAnswer(filters, "filter");
    }

    public static void currentFilter() {
        ArrayList<String> currentFilters = new ArrayList<>();
        for (Filter filter : filters) {
            currentFilters.add(filter.toString());
        }
        sendAnswer(currentFilters, "filter");
    }
}//end OffController class
