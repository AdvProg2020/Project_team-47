package controller.panels.seller;

import controller.Command;
import controller.Controller;
import model.discount.Off;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.*;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.others.request.EditOffRequest;
import model.others.request.Request;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class ManageOffCommands extends Command {
    public static ShowOffsCommand getShowOffsCommand() {
        return ShowOffsCommand.getInstance();
    }

    public static ViewOffCommand getViewOffCommand() {
        return ViewOffCommand.getInstance();
    }

    public static EditOffCommand getEditOffCommand() {
        return EditOffCommand.getInstance();
    }

    public static AddOffCommand getAddOffCommand() {
        return AddOffCommand.getInstance();
    }
}


class ShowOffsCommand extends ManageOffCommands {
    private static ShowOffsCommand command;

    private ShowOffsCommand() {
        this.name = "view offs seller";
    }

    public static ShowOffsCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowOffsCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws InvalidSortException {
        return showSellerOffs(request.getHashMap().get("sort field"), request.getHashMap().get("sort direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage showSellerOffs(String sortField, String sortDirection) throws InvalidSortException {
        if (sortField != null && sortDirection != null) {
            sortDirection = sortDirection.toLowerCase();
            sortField = sortField.toLowerCase();
        }

        if (!checkSort(sortField, sortDirection, "off")) {
            throw new InvalidSortException();
        } else {
            return sendAnswer(((Seller) getLoggedUser()).getAllOffsInfo(sortField, sortDirection), "off");
        }
    }
}


class ViewOffCommand extends ManageOffCommands {
    private static ViewOffCommand command;

    private ViewOffCommand() {
        this.name = "view off seller";
    }

    public static ViewOffCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewOffCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, OffDoesntExistException {
        containNullField(request.getHashMap().get("off id"));
        return showOff(request.getHashMap().get("off id"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage showOff(String offId) throws OffDoesntExistException {
        if (!((Seller) getLoggedUser()).sellerHasThisOff(offId)) {
            throw new OffDoesntExistException();
        } else {
            return sendAnswer(((Seller) getLoggedUser()).getOffInfo(offId));
        }
    }
}


class EditOffCommand extends ManageOffCommands {
    private static EditOffCommand command;
    private Off off;
    private EditOffRequest editOffRequest;

    private EditOffCommand() {
        this.name = "edit off";
    }

    public static EditOffCommand getInstance() {
        if (command != null)
            return command;
        command = new EditOffCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, OffDoesntExistException,
            ProductDoesntExistException, CommonException, DateException, NumberException {
        containNullField(request.getHashMap().get("off id"), request.getHashMap().get("field"));
        containNullField(request.getHashMap().get("new value"), request.getHashMap().get("change type"));
        editOff(request.getHashMap().get("off id"), request.getHashMap().get("field"), request);
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void editOff(String offId, String field, ClientMessage req) throws OffDoesntExistException, NumberException, DateException,
            CommonException, ProductDoesntExistException {
        off = ((Seller) getLoggedUser()).getOffById(offId);
        switch (field) {
            case "start-time" -> editStartTime(req.getHashMap().get("new value"));
            case "finish-time" -> editFinishTime(req.getHashMap().get("new value"));
            case "percent" -> editPercent(req.getHashMap().get("new value"));
            case "products" -> editProduct(req.getHashMap().get("new value"), req.getHashMap().get("change type"));
        }
    }

    private void editProduct(String productId, String changeType) throws ProductDoesntExistException, CommonException {
        Product.getProductWithId(productId);
        if (!(changeType.equals("add") || changeType.equals("remove")))
            throw new CommonException("Wrong type!!");
        editOffRequest = new EditOffRequest("product", productId, changeType);
        createRequest();
    }

    private void editPercent(String percentString) throws NumberException {
        try {
            Integer.parseInt(percentString);
        } catch (NumberFormatException e) {
            throw new NumberException("Please enter valid number!!");
        }
        editOffRequest = new EditOffRequest("percent", percentString);
        createRequest();
    }

    private void createRequest() {
        Request request = new Request();
        request.setRequestSender(getLoggedUser());
        request.setType("edit-off");
        editOffRequest.setOffId(off.getOffId());
        request.setMainRequest(editOffRequest);
        request.addToDatabase();

        off.setOffStatus("IN_EDITING_QUEUE");
    }

    private void editFinishTime(String finishTime) throws DateException {
        canEditOffTime(finishTime, "finish-time");
        editOffRequest = new EditOffRequest("finish-time", finishTime);
        createRequest();
    }

    private void editStartTime(String startTime) throws DateException {
        canEditOffTime(startTime, "start-time");
        editOffRequest = new EditOffRequest("start-time", startTime);
        createRequest();
    }

    private void canEditOffTime(String newValue, String field) throws DateException {
        if (!Controller.isDateFormatValid(newValue)) throw new DateException();

        Date date = Controller.getDateWithString(newValue);

        if (field.equals("start-time") && off.isOffStarted()) {
            throw new DateException("This off started now and you can't change it's starting time!");
        } else if (field.equals("start-time") && date.before(Controller.getCurrentTime())) {
            throw new DateException("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && off.isOffFinished()) {
            throw new DateException("This off finished now and you can't change it!!");
        } else if (field.equals("finish-time") && date.before(Controller.getCurrentTime())) {
            throw new DateException("Can't change finish time to this value!!");
        } else if (field.equals("start-time") && date.after(off.getFinishTime())) {
            throw new DateException("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && date.before(off.getStartTime())) {
            throw new DateException("Can't change finish time to this value!!");
        }
    }
}


class AddOffCommand extends ManageOffCommands {
    private static AddOffCommand command;
    int percent;
    Date startDate;
    Date finishDate;

    private AddOffCommand() {
        this.name = "add off";
    }

    public static AddOffCommand getInstance() {
        if (command != null)
            return command;
        command = new AddOffCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap(), request.getArrayList());
        checkPrimaryErrors(request);
        addOff(request.getHashMap(), request.getArrayList());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        //check that if HashMap has all required key
        String[] offInfoKey = {"start-time", "finish-time", "percent"};
        for (String key : offInfoKey) {
            if (request.getHashMap().containsKey(key)) throw new NotEnoughInformation();
        }
        startDate = getDateWithString(request.getHashMap().get("start-time"));
        finishDate = getDateWithString(request.getHashMap().get("finish-date"));
        if (startDate.before(getCurrentTime()) ||
                finishDate.before(getCurrentTime()) ||
                !startDate.before(finishDate)) throw new DateException();
        try {
            percent = Integer.parseInt(request.getHashMap().get("percent"));
            if (percent >= 100 || percent <= 0) {
                throw new InvalidPercentException();
            }
        } catch (NumberFormatException e) {
            throw new NumberException();
        }
    }

    public void addOff(HashMap<String, String> offInfo, ArrayList<String> products) throws CommonException, DateException {
        for (String productId : products)
            if (!((Seller) getLoggedUser()).hasProduct(productId))
                throw new CommonException("You don't have product with this id: " + productId + "!!");
        ((Seller) getLoggedUser()).addOff(offInfo, products);
    }
}

