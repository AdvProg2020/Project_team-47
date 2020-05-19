package controller.panels.seller;

import controller.Command;
import controller.Controller;
import controller.Error;
import model.discount.Off;
import model.others.Product;
import model.send.receive.ClientMessage;
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

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Seller)) {
            sendError(Error.NEED_SELLER.getError());
            return false;
        }
        return true;
    }
}


class ShowOffsCommand extends ManageOffCommands {
    private static ShowOffsCommand command;

    private ShowOffsCommand() {
        this.name = "view offs";
    }

    public static ShowOffsCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowOffsCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        showSellerOffs(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void showSellerOffs(String sortField, String sortDirection) {
        if (sortField != null && sortDirection != null) {
            sortDirection = sortDirection.toLowerCase();
            sortField = sortField.toLowerCase();
        }

        if (!checkSort(sortField, sortDirection, "off")) {
            sendError("Can't sort with this field and direction!!");
        } else {
            sendAnswer(((Seller) getLoggedUser()).getAllOffsInfo(sortField, sortDirection), "off");
        }
    }
}


class ViewOffCommand extends ManageOffCommands {
    private static ViewOffCommand command;

    private ViewOffCommand() {
        this.name = "view off";
    }

    public static ViewOffCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewOffCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        showOff(request.getArrayList().get(0));
    }

    private void showOff(String offId) {
        if (!((Seller) getLoggedUser()).sellerHasThisOff(offId)) {
            sendError("You don't have this off!!");
        } else {
            sendAnswer(((Seller) getLoggedUser()).getOffInfo(offId));
        }
    }
}


class EditOffCommand extends ManageOffCommands {
    private static EditOffCommand command;

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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        ArrayList<String> reqInfo = getReqInfo(request);
        if (containNullField(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2)) || containNullField(request.getObject()))
            return;
        editOff(reqInfo.get(0), reqInfo.get(1), reqInfo.get(2), request.getObject());
    }

    private void editOff(String offId, String field, String type, Object newValue) {
        //this function will check that if off can be edited with this value and
        //if yes then it calls editOff function on seller to creating request

        Off off = ((Seller) getLoggedUser()).getOffById(offId);
        if (off == null) {
            sendError("There isn't off with this id in your offs!!");
            return;
        }
        if (!canEditOff(field, type, newValue, off)) {
            return;
        }

        ((Seller) getLoggedUser()).editOff(off, field, newValue, type);
    }

    private boolean canEditOff(String field, String type, Object newValue, Off off) {
        switch (field) {
            case "start-time":
            case "finish-time":
                return canEditOffTime(newValue, off, field);

            case "percent":
                try {
                    int temp = Integer.parseInt((String) newValue);
                    if (temp < 100 && temp > 0) {
                        return true;
                    }
                    sendError("Percent should be between 0 and 100!!");
                    return false;
                } catch (NumberFormatException e) {
                    sendError("Wrong number!!");
                    return false;
                }

            case "products":
                return canEditProductInOff(off, newValue, type);
        }

        return false;
    }

    private boolean canEditProductInOff(Off off, Object newValue, String type) {
        ArrayList<String> productsId;
        try {
            productsId = (ArrayList<String>) newValue;
        } catch (ClassCastException e) {
            sendError("Wrong command!!");
            return false;
        }

        //finding products by their id and save it to products array list
        ArrayList<Product> products = new ArrayList<>();
        for (String id : productsId) {
            Product product = Product.getProductWithId(id);
            if (product == null) {
                sendError("There isn't product with this id: " + id + " !!");
                return false;
            } else
                products.add(product);
        }

        if (type == null) {
            sendError("Wrong command!!");
            return false;
        }

        switch (type) {
            case "append":
                for (Product product : products) {
                    if (off.isItInOff(product)) {
                        products.remove(product);
                    }
                }
            case "replace":
                return true;
            case "remove":
                for (Product product : products) {
                    if (!off.isItInOff(product)) {
                        sendError("Some of this products doesn't exist on off's products!!");
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    private boolean canEditOffTime(Object newValue, Off off, String field) {
        int flag = 1;
        if (!(newValue instanceof String)) {
            sendError("Wrong command!!");
        } else if (!Controller.isDateFormatValid((String) newValue)) {
            sendError("Wrong format for date!!");
        }

        Date date = Controller.getDateWithString((String) newValue);

        if (field.equals("start-time") && off.isOffStarted()) {
            sendError("This off started now and you can't change it's starting time!");
        } else if (field.equals("start-time") && date.before(Controller.getCurrentTime())) {
            sendError("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && off.isOffFinished()) {
            sendError("This off finished now and you can't change it!!");
        } else if (field.equals("finish-time") && date.before(Controller.getCurrentTime())) {
            sendError("Can't change finish time to this value!!");
        } else if (field.equals("start-time") && date.after(off.getFinishTime())) {
            sendError("Can't change start time to this value!!");
        } else if (field.equals("finish-time") && date.before(off.getStartTime())) {
            sendError("Can't change finish time to this value!!");
        } else {
            flag = 0;
        }
        return flag == 0;
    }
}


class AddOffCommand extends ManageOffCommands {
    private static AddOffCommand command;

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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstHashMap(), request.getArrayList()))
            return;
        addOff(request.getFirstHashMap(), request.getArrayList());
    }

    public void addOff(HashMap<String, String> offInformationHashMap, ArrayList<String> products) {
        if (!isOffInfoValid(offInformationHashMap))
            return;

        for (String productId : products) {
            if (!((Seller) getLoggedUser()).hasProduct(productId)) {
                sendError("You don't have product with this id: " + productId + "!!");
                return;
            }
        }
        ((Seller) getLoggedUser()).addOff(offInformationHashMap, products);

    }

    private boolean isOffInfoValid(HashMap<String, String> offInfo) {
        //check that if HashMap has all required key
        String[] offInfoKey = {"start-time", "finish-time", "percent"};
        for (String key : offInfoKey) {
            if (offInfo.containsKey(key)) {
                sendError("Not enough information!!");
                return false;
            }
        }
        if (!isOffDatesValid(offInfo.get("start-time"), offInfo.get("finish-time")))
            return false;
        else return isPercentValid(offInfo.get("percent"));

        //all error that could be happened checked and didn't find any error so function should return true
    }

    private boolean isPercentValid(String percentString) {
        //check that if percent is valid
        try {
            int percent = Integer.parseInt(percentString);
            if (percent >= 100 || percent <= 0) {
                sendError("Percent should be a number between 0 and 100!!");
                return false;
            }
        } catch (NumberFormatException e) {
            sendError("Please enter valid number for off percent!!");
            return false;
        }
        return true;
    }

    private boolean isOffDatesValid(String start, String finish) {
        //check that if starting and finishing date is valid

        if (!Controller.isDateFormatValid(start) ||
                !Controller.isDateFormatValid(finish)) {

            sendError("Wrong date format!!");
            return false;
        }


        Date startingDate = Controller.getDateWithString(start);
        Date finishingDate = Controller.getDateWithString(finish);
        Date currentDate = Controller.getCurrentTime();
        if (startingDate.before(currentDate) ||
                finishingDate.before(currentDate) ||
                !startingDate.before(finishingDate)) {

            sendError("Wrong Date!!");
            return false;
        }

        return true;
    }

}

