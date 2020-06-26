package controller.off;

import controller.Command;
import controller.Controller;
import model.ecxeption.Exception;
import model.ecxeption.common.DateException;
import model.ecxeption.common.InvalidPercentException;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.common.NumberException;
import model.ecxeption.filter.FilterExistException;
import model.ecxeption.filter.FilterNotExistException;
import model.ecxeption.filter.WrongFilterException;
import model.ecxeption.user.UserNotExistException;
import model.others.ClientFilter;
import model.others.Filter;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;

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
    public ServerMessage process(ClientMessage request) {
        if ("show available filters offs".equals(request.getType())) {
            return showAvailableFilters();
        } else {
            return currentFilters();
        }
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage showAvailableFilters() {
        ArrayList<ClientFilter> filters = new ArrayList<>();
        filters.add(new ClientFilter("Percent", "numeric", "%", "Percent"));
        filters.add(new ClientFilter("Time", "date", "", "Start Time", "Finish Time"));
        filters.add(new ClientFilter("Seller", "text", "", "Seller Username"));
        filters.add(new ClientFilter("Off Status", "check box", "",
                "In Edit Queue", "Edit Accepted", "Edit Declined", "Confirmed"));
        return sendAnswer(filters, "filter");
    }

    private ServerMessage currentFilters() {
        return null;
//        ArrayList<String> currentFilters = new ArrayList<>();
//        for (Filter filter : filters()) {
//            currentFilters.add(filter.toString());
//        }
//        return sendAnswer(currentFilters, "filter");
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
    public ServerMessage process(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo.get("filter key"), reqInfo.get("first value"), reqInfo.get("second value"));
        checkPrimaryErrors(request);
        return addFilter(reqInfo.get("filter key"), reqInfo.get("first value"), reqInfo.get("second value"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        if (isFilterExist(reqInfo.get("filter key"))) {
            throw new FilterExistException();
        }
    }


    private ServerMessage addFilter(String filterKey, String firstFilterValue, String secondFilterValue) throws Exception {
        switch (filterKey) {
            case "time" -> {
                if (!Controller.isDateFormatValid(firstFilterValue) || !Controller.isDateFormatValid(secondFilterValue)) {
                    throw new DateException();
                } else
                    createFilterForTime(firstFilterValue, secondFilterValue);
            }
            case "percent" -> {
                try {
                    int min = Integer.parseInt(firstFilterValue);
                    int max = Integer.parseInt(secondFilterValue);
                    createFilterForPercent(min, max);
                } catch (NumberFormatException e) {
                    throw new NumberException();
                }
            }
            case "seller" -> createFilterForSeller(firstFilterValue);
            case "off-status" -> createFilterForOffStatus(firstFilterValue);
            default -> throw new WrongFilterException();
        }
        return actionCompleted();
    }

    private void createFilterForOffStatus(String status) throws Exception {
        switch (status) {
            case "in-edit-queue":
            case "started":
            case "finished":
            case "accepted-by-manager":
                break;
            default:
                throw new Exception("Wrong value!");
        }
        Filter filter = new Filter();
        filter.setType("equality");
        filter.setFilterKey("status");
        filter.setFirstFilterValue(status);
        addFilter(filter);
    }

    private void createFilterForSeller(String sellerUsername) throws UserNotExistException {
        if (!Seller.isThereSeller(sellerUsername)) {
            throw new UserNotExistException();
        } else {
            Filter filter = new Filter();
            filter.setType("equality");
            filter.setFilterKey("seller");
            filter.setFirstFilterValue(sellerUsername);
            addFilter(filter);
        }
    }

    private void createFilterForPercent(int min, int max) throws InvalidPercentException {
        if (min < 0 || max <= min || max > 100) {
            throw new InvalidPercentException();
        } else {
            Filter filter = new Filter();
            filter.setType("equation");
            filter.setFilterKey("percent");
            filter.setFirstInt(min);
            filter.setSecondInt(max);
            addFilter(filter);
        }
    }

    private void createFilterForTime(String startDate, String finishDate) throws DateException {
        Date start = Controller.getDateWithString(startDate);
        Date finish = Controller.getDateWithString(finishDate);
        if (start == null || finish == null)
            throw new DateException();
        if (start.after(finish))
            throw new DateException();

        Filter filter = new Filter();
        filter.setType("equation");
        filter.setFilterKey("time");
        filter.setFirstFilterValue(startDate);
        filter.setSecondFilterValue(finishDate);
        addFilter(filter);
    }

    private boolean isFilterExist(String key) {
        //check that if there is a filter with given key
        switch (key) {
            case "time", "percent", "seller", "off-status" -> {
                for (Filter filter : filters()) {
                    if (filter.getFilterKey().equals(key)) {
                        return true;
                    }
                }
                return false;
            }

            default -> {
                return true;
            }
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, FilterNotExistException {
        containNullField(request.getHashMap(), request.getHashMap().get("filter key"));
        disableFilter(request.getHashMap().get("filter key").toLowerCase());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) {
    }

    private void disableFilter(String filterKey) throws FilterNotExistException {
        for (Filter filter : filters()) {
            if (filter.getFilterKey().equals(filterKey)) {
                removeFilter(filter);
                actionCompleted();
                return;
            }
        }
        throw new FilterNotExistException();
    }

}

