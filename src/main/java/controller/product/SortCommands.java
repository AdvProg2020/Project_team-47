package controller.product;

import controller.Command;
import model.send.receive.ClientMessage;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static controller.Controller.*;

public abstract class SortCommands extends Command {
    public static SortCommonCommand getSortCommonCommand() {
        return SortCommonCommand.getInstance();
    }

    public static SortCommand getSortCommand() {
        return SortCommand.getInstance();
    }

    protected String sortField() {
        return AllProductsController.getInstance().sortField();
    }

    protected String sortDirection() {
        return AllProductsController.getInstance().sortDirection();
    }

    protected void setSort(String sortField, String sortDirection) {
        AllProductsController.getInstance().setSort(sortField, sortDirection);
    }
}

class SortCommonCommand extends SortCommands {
    private static SortCommonCommand command;

    private SortCommonCommand() {
        this.name = "(show available sorts products|show current sort products|disable sort products)";
    }

    public static SortCommonCommand getInstance() {
        if (command != null)
            return command;
        command = new SortCommonCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        switch (request.getRequest()) {
            case "show available sorts products":
                showAvailableSorts();
                break;
            case "show current sort products":
                currentSort();
                break;
            case "disable sort products":
                disableSort();
                break;
        }
    }


    private void showAvailableSorts() {
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

    private void currentSort() {
        if (sortField() == null || sortDirection() == null) {
            sendError("You didn't any field to sort offs!!");
        } else
            sendAnswer(sortField(), sortDirection());
    }

    private void disableSort() {
        setSort(null, null);
        actionCompleted();
    }
}

class SortCommand extends SortCommands {
    private static SortCommand command;

    private SortCommand() {
        this.name = "sort an available sort products";
    }

    public static SortCommand getInstance() {
        if (command != null)
            return command;
        command = new SortCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString(), request.getSecondString()))
            return;
        sort(request.getFirstString().toLowerCase(), request.getSecondString().toLowerCase());
    }

    public void sort(String sortField, String sortDirection) {
        if (Pattern.matches("(name|score|seen-time|price)", sortField()) &&
                Pattern.matches("(ascending|descending)", sortDirection())) {

            setSort(sortField, sortDirection);
            actionCompleted();
        } else
            sendError("Can't sort with this field and direction!!");
    }
}
