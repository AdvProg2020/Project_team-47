package controller.off;

import controller.Controller;
import model.others.Filter;

import java.util.ArrayList;

public class OffController extends Controller {
    private static OffController offController;
    private ArrayList<Filter> filters;
    private String sortField;
    private String sortDirection;

    private OffController() {
        filters = new ArrayList<>();
        commands = new ArrayList<>();
        initializeOffCommands();
        initializeFilterCommands();
        initializeSortCommands();
    }

    public static OffController getInstance() {
        if (offController != null)
            return offController;
        offController = new OffController();
        return offController;
    }

    private void initializeSortCommands() {
        commands.add(SortCommands.getSortCommand());
        commands.add(SortCommands.getSortCommonCommand());
    }

    private void initializeFilterCommands() {
        commands.add(FilterCommands.getAddFilterCommand());
        commands.add(FilterCommands.getDisableFilterCommand());
        commands.add(FilterCommands.getFilterCommonCommands());
    }

    private void initializeOffCommands() {
        commands.add(OffCommands.getInitializePage());
        commands.add(OffCommands.getShowOffCommand());
    }

    void resetFilters() {
        if (getLoggedUser() != null) {
            getLoggedUser().resetProductFilters();
        } else {
            filters = new ArrayList<>();
        }
    }

    String sortField() {
        if (loggedUser != null) {
            return loggedUser.getSortField();
        } else {
            return sortField;
        }
    }

    String sortDirection() {
        if (loggedUser != null) {
            return loggedUser.getSortDirection();
        } else {
            return sortDirection;
        }
    }

    ArrayList<Filter> filters() {
        if (loggedUser != null) {
            return loggedUser.getOffFilters();
        } else {
            return filters;
        }
    }

    void removeFilter(Filter filter) {
        if (loggedUser != null) {
            loggedUser.removeOffFilter(filter);
        } else {
            filters.remove(filter);
        }
    }

    void addFilter(Filter filter) {
        if (loggedUser != null) {
            loggedUser.addOffFilter(filter);
        } else {
            filters.add(filter);
        }
    }

    void setSort(String field, String direction) {
        if (loggedUser != null) {
            loggedUser.setSortField(field);
            loggedUser.setSortDirection(direction);
        } else {
            sortField = field;
            sortDirection = direction;
        }
    }

}//end OffController class
