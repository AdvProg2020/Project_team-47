package controller.off;

import controller.Command;
import model.send.receive.ClientMessage;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static controller.Controller.*;

public abstract class SortCommands extends Command {
    public static SortCommand getSortCommand() {
        return SortCommand.getInstance();
    }

    public static SortCommonCommand getSortCommonCommand() {
        return SortCommonCommand.getInstance();
    }

    protected String sortField() {
        return OffController.getInstance().sortField();
    }

    protected String sortDirection() {
        return OffController.getInstance().sortDirection();
    }

    protected void setSort(String sortField, String sortDirection) {
        OffController.getInstance().setSort(sortField, sortDirection);
    }
}


class SortCommand extends SortCommands {
    private static SortCommand command;

    private SortCommand() {
        this.name = "sort an available sort offs";
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
        if (Pattern.matches("(percent|start-time|finish-time)", sortField) &&
                Pattern.matches("(ascending|descending)", sortDirection)) {

            setSort(sortField, sortDirection);
            actionCompleted();
        } else
            sendError("Can't sort with this field and direction!!");
    }

}

class SortCommonCommand extends SortCommands {
    private static SortCommonCommand command;

    private SortCommonCommand() {
        this.name = "(show available sorts offs|show current sort offs|disable sort offs)";
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
            case "show available sorts offs":
                showAvailableSorts();
                break;
            case "show current sort offs":
                currentSort();
                break;
            case "disable sort offs":
                disableSort();
                break;
        }
    }

    private void disableSort() {
        setSort(null, null);
        actionCompleted();
    }

    private void showAvailableSorts() {
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("Percent");
        sorts.add("Starting Time");
        sorts.add("Finish Time");

        sendAnswer(sorts, "sort");
    }

    private void currentSort() {
        if (sortField() == null || sortDirection() == null) {
            sendError("You didn't any field to sort offs!!");
        } else
            sendAnswer(sortField(), sortDirection());
    }

}