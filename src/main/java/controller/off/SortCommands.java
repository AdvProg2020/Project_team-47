package controller.off;

import controller.Command;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, InvalidSortException {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo, reqInfo.get("sort field"), reqInfo.get("sort direction"));
        sort(reqInfo.get("sort field"), reqInfo.get("sort direction"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
    }

    public void sort(String sortField, String sortDirection) throws InvalidSortException {
        if (Pattern.matches("(percent|start-time|finish-time)", sortField) &&
                Pattern.matches("(ascending|descending)", sortDirection)) {

            setSort(sortField, sortDirection);
            actionCompleted();
        } else
            throw new InvalidSortException();
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
    public ServerMessage process(ClientMessage request) throws Exception {
        return switch (request.getRequest()) {
            case "show available sorts offs" -> showAvailableSorts();
            case "show current sort offs" -> currentSort();
            case "disable sort offs" -> disableSort();
            default -> throw new Exception("Error");
        };
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage disableSort() {
        setSort(null, null);
        return actionCompleted();
    }

    private ServerMessage showAvailableSorts() {
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("Percent");
        sorts.add("Starting Time");
        sorts.add("Finish Time");

        return sendAnswer(sorts, "sort");
    }

    private ServerMessage currentSort() throws Exception {
        if (sortField() == null || sortDirection() == null) {
            throw new Exception("Error!!");
        } else
            return sendAnswer(sortField(), sortDirection());
    }

}