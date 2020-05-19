package controller.off;

import controller.Command;
import controller.Controller;
import model.others.Filter;
import model.send.receive.ClientMessage;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;

import static controller.Controller.*;

public abstract class FilterCommands extends Command {
    public static FilterCommonCommands getFilterCommonCommands() {
        return FilterCommonCommands.getInstance();
    }

    public static AddFilterCommand getAddFilterCommand() {
        return AddFilterCommand.getInstance();
    }

    public static DisableFilterCommand getDisableFilterCommand() {
        return DisableFilterCommand.getInstance();
    }

    protected ArrayList<Filter> filters() {
        return OffController.getInstance().filters();
    }

    protected void addFilter(Filter filter) {
        OffController.getInstance().addFilter(filter);
    }

    protected void removeFilter(Filter filter) {
        OffController.getInstance().removeFilter(filter);
    }
}


class FilterCommonCommands extends FilterCommands {
    private static FilterCommonCommands command;

    private FilterCommonCommands() {
        this.name = "(show available filters offs|current filters offs)";
    }

    public static FilterCommonCommands getInstance() {
        if (command != null)
            return command;
        command = new FilterCommonCommands();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        switch (request.getRequest()) {
            case "show available filters offs":
                showAvailableFilters();
                break;
            case "current filters offs":
                currentFilters();
                break;
        }
    }

    private void showAvailableFilters() {
        ArrayList<String> filters = new ArrayList<>();
        filters.add("Percent");
        filters.add("Start and Finish Time");
        filters.add("Seller Username");
        filters.add("Off status(IN_EDIT_QUEUE | EDIT_ACCEPTED | EDIT_DECLINED | CONFIRMED) ");

        sendAnswer(filters, "filter");
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
        this.name = "filter an available filter offs";
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
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)))
            return;
        addFilter(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2));
    }


    private void addFilter(String filterKey, String firstFilterValue, String secondFilterValue) {
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

    private void createFilterForOffStatus(String status) {
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
        addFilter(filter);
        actionCompleted();
    }

    private void createFilterForSeller(String sellerUsername) {
        if (!Seller.isThereSeller(sellerUsername)) {
            sendError("There isn't any seller with this username");
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("seller");
            filter.setFirstFilterValue(sellerUsername);
            addFilter(filter);
        }
    }

    private void createFilterForPercent(int min, int max) {
        if (min < 0 || max <= min || max > 100) {
            sendError("Please enter valid percent!!");
        } else {
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("percent");
            filter.setFirstInt(min);
            filter.setSecondInt(max);
            addFilter(filter);
            actionCompleted();
        }
    }

    private void createFilterForTime(String startDate, String finishDate) {
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
        addFilter(filter);
        actionCompleted();
    }

    private boolean isFilterExist(String key) {
        //check that if there is a filter with given key
        switch (key) {
            case "time":
            case "percent":
            case "seller":
            case "off-status":
                for (Filter filter : filters()) {
                    if (filter.getFilterKey().equals(key)) {
                        return true;
                    }
                }
                return false;
            default:
                return true;
        }
    }
}

class DisableFilterCommand extends FilterCommands {
    private static DisableFilterCommand command;

    private DisableFilterCommand() {
        this.name = "disable a selected filter offs";
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
        disableFilter(request.getFirstString().toLowerCase());
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

