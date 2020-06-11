package controller.product;

import controller.Command;
import model.ecxeption.DebugException;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;

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
        this.name = "(show available sorts products|disable sort products)";
    }

    public static SortCommonCommand getInstance() {
        if (command != null)
            return command;
        command = new SortCommonCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws DebugException {
        return switch (request.getType()) {
            case "show available sorts products" -> showAvailableSorts();
            case "disable sort products" -> disableSort();
            default -> throw new DebugException();
        };
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }


    private ServerMessage showAvailableSorts() {
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("Name");
        sorts.add("Score");
        sorts.add("Seen Time");
        sorts.add("Price");
        sorts.add("Seller");
        sorts.add("Brand");
        sorts.add("Availability");

        return sendAnswer(sorts, "sort");
    }


    private ServerMessage disableSort() {
        setSort(null, null);
        return actionCompleted();
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, InvalidSortException {
        containNullField(request.getHashMap().get("field"), request.getHashMap().get("direction"));
        sort(request.getHashMap().get("field"), request.getHashMap().get("direction"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    public void sort(String sortField, String sortDirection) throws InvalidSortException {
        if (Pattern.matches("(name|score|seen-time|price)", sortField) &&
                Pattern.matches("(ascending|descending)", sortDirection)) {

            setSort(sortField, sortDirection);
            actionCompleted();
        } else
            throw new InvalidSortException();
    }
}
