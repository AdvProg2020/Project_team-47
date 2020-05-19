package controller.panels.manager;

import controller.Command;
import controller.Controller;
import controller.Error;
import model.discount.DiscountCode;
import model.send.receive.ClientMessage;
import model.user.Customer;
import model.user.Manager;
import model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class DiscountCodeCommands extends Command {
    public static CreateCodeCommand getCreateCodeCommand() {
        return CreateCodeCommand.getInstance();
    }

    public static ViewCodesCommand getViewCodesCommand() {
        return ViewCodesCommand.getInstance();
    }

    public static ViewCodeCommand getViewCodeCommand() {
        return ViewCodeCommand.getInstance();
    }

    public static EditCodeCommand getEditCodeCommand() {
        return EditCodeCommand.getInstance();
    }

    public static RemoveCodeCommand getRemoveCodeCommand() {
        return RemoveCodeCommand.getInstance();
    }

    public static GiveGiftCommand getGiveGiftCommand() {
        return GiveGiftCommand.getInstance();
    }

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Manager)) {
            sendError(Error.NEED_MANGER.getError());
            return false;
        }
        return true;
    }

}

class CreateCodeCommand extends DiscountCodeCommands {
    private static CreateCodeCommand command;

    private CreateCodeCommand() {
        this.name = "create discount code";
    }

    public static CreateCodeCommand getInstance() {
        if (command != null)
            return command;
        command = new CreateCodeCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstHashMap(), request.getArrayList()))
            return;
        createDiscountCode(request.getFirstHashMap(), request.getArrayList());
    }


    private void createDiscountCode(HashMap<String, String> discountInfo, ArrayList<String> usernames) {
        if (discountCodeInfoHasError(discountInfo, usernames))
            return;

        ArrayList<Customer> users = new ArrayList<>();
        for (String username : usernames) {
            Customer user = (Customer) User.getUserByUsername(username);
            if (user != null)
                users.add(user);
        }

        createDiscountCodeAfterChecking(discountInfo, users);
    }

    private boolean discountCodeInfoHasError(HashMap<String, String> discountInfo, ArrayList<String> usernames) {
        if (discountInfoHasError(discountInfo))
            return true;
        else if (discountUsersHasError(usernames))
            return true;
        else if (discountDateHasError(discountInfo.get("start-time"), discountInfo.get("finish-time")))
            return true;
        else
            return discountInfoHasWrongIntegers(discountInfo);
    }


    boolean discountInfoHasError(HashMap<String, String> discountInfo) {
        //checking that discount info HashMap contains all required key
        String[] discountKey = {"start-time", "finish-time", "max-usable-time", "max-discount-amount", "percent"};
        for (String key : discountKey) {
            if (!discountInfo.containsKey(key)) {
                sendError("Not enough information!!");
                return true;
            }
        }
        return false;
    }


    private boolean discountUsersHasError(ArrayList<String> usernames) {
        //check that users who could use code exists and check start and finish time
        if (User.isThereCustomersWithUsername(usernames)) {
            sendError("There isn't customer for some of username you entered!!");
            return true;
        }
        return false;
    }


    boolean discountDateHasError(String start, String finish) {
        //this function will check that discount starting and finishing time entered correctly

        if (!(Controller.isDateFormatValid(start) && Controller.isDateFormatValid(finish))) {
            sendError("Wrong format for date!!");
            return false;
        }

        Date startDate = Controller.getDateWithString(start);
        Date finishDate = Controller.getDateWithString(finish);

        if (startDate.before(Controller.getCurrentTime())) {
            sendError("Can't set start time to this value!!");
            return false;
        } else if (finishDate.before(Controller.getCurrentTime())) {
            sendError("Can't set finish time to this value!!");
            return false;
        } else if (!startDate.before(finishDate)) {
            sendError("Start time should be before finish time!!");
            return false;
        }
        return true;
    }


    boolean discountInfoHasWrongIntegers(HashMap<String, String> discountInfo) {
        //check that integer value entered correctly
        try {
            int percent = Integer.parseInt(discountInfo.get("percent"));
            int maxDiscountAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
            int maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));

            if (percent >= 100 || percent <= 0) {
                sendError("Percent should be a number between 0 and 100!!");
                return true;
            } else if (maxDiscountAmount <= 0) {
                sendError("Max discount amount should be positive!!");
                return true;
            } else if (maxUsableTime <= 0) {
                sendError("Max usable time should be positive!!");
                return true;
            }
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return true;
        }

        return false;
    }


    void createDiscountCodeAfterChecking(HashMap<String, String> discountInfo, ArrayList<Customer> users) {
        int maxUsableTime, maxDiscountAmount, percent;
        percent = Integer.parseInt(discountInfo.get("percent"));
        maxDiscountAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
        maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));

        DiscountCode discountCode = new DiscountCode(maxDiscountAmount, maxUsableTime);
        discountCode.setStartTime(Controller.getDateWithString(discountInfo.get("start-time")));
        discountCode.setFinishTime(Controller.getDateWithString(discountInfo.get("finish-time")));
        discountCode.setUsersAbleToUse(users);
        discountCode.setPercent(percent);
        discountCode.updateDatabase();
        for (Customer user : users) {
            user.addDiscountCode(discountCode);
            user.updateDatabase().update();
        }

        sendAnswer(discountCode.getDiscountCode());
    }

}//end CreateDiscountCodeCommand class


class ViewCodesCommand extends DiscountCodeCommands {
    private static ViewCodesCommand command;

    private ViewCodesCommand() {
        this.name = "view discount codes manager";
    }

    public static ViewCodesCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewCodesCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        viewAllDiscountCodes(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void viewAllDiscountCodes(String field, String direction) {
        if (field != null && direction != null) {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
        }

        if (!checkSort(field, direction, "discount-code")) {
            sendError(Error.CANT_SORT.getError());
        } else
            sendAnswer(DiscountCode.getAllDiscountCodeInfo(field, direction), "code");
    }
}//end ViewCodesCommand class


class ViewCodeCommand extends DiscountCodeCommands {
    private static ViewCodeCommand command;

    private ViewCodeCommand() {
        this.name = "view discount code manager";
    }

    public static ViewCodeCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewCodeCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        viewDiscountCode(request.getArrayList().get(0).toLowerCase());
    }

    private void viewDiscountCode(String code) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
        } else
            sendAnswer(DiscountCode.getDiscountById(code).discountCodeInfo());
    }
}//end ViewCodeCommands class


class EditCodeCommand extends DiscountCodeCommands {
    private static EditCodeCommand command;

    private EditCodeCommand() {
        this.name = "edit discount code manager";
    }

    public static EditCodeCommand getInstance() {
        if (command != null)
            return command;
        command = new EditCodeCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0), request.getArrayList().get(1), request.getArrayList().get(2)))
            return;
        editDiscountCode(request.getArrayList().get(0), request.getArrayList().get(1).toLowerCase(), request.getArrayList().get(2));
    }

    public void editDiscountCode(String code, String field, String newValue) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
            return;
        }

        DiscountCode discountCode = DiscountCode.getDiscountById(code);
        switch (field) {
            case "start-time":
                editCodeTime(discountCode, newValue, "Starting time");
                return;
            case "finish-time":
                editCodeTime(discountCode, newValue, "Finishing time");
                return;
            case "max-discount-amount":
                editCodeIntegersValues(discountCode, "max-discount-amount", newValue);
                break;
            case "percent":
                editCodeIntegersValues(discountCode, "percent", newValue);
                break;
            case "max-usable-time":
                editCodeIntegersValues(discountCode, "max-usable-time", newValue);
                break;
            default:
                sendError("You can't change this field!!");
        }
        discountCode.updateDatabase();
    }

    private void editCodeTime(DiscountCode discountCode, String timeString, String type) {
        if (!Controller.isDateFormatValid(timeString)) {
            sendError("Please enter right value!!");
            return;
        }
        Date time = Controller.getDateWithString(timeString);
        if (time.before(Controller.getCurrentTime())) {
            sendError(type + " can't change to this value!!");
            return;
        }
        switch (type) {
            case "Starting time":
                discountCode.setStartTime(time);
                break;
            case "Finishing time":
                discountCode.setFinishTime(time);
                break;
        }
        actionCompleted();
    }

    private void editCodeIntegersValues(DiscountCode discountCode, String type, String integerString) {
        int integer;
        try {
            integer = Integer.parseInt(integerString);
        } catch (NumberFormatException e) {
            sendError("Please enter a valid number!!");
            return;
        }

        if (integer <= 0) {
            sendError("Please enter a positive value!!");
            return;
        }
        switch (type) {
            case "max-usable-time":
                discountCode.setMaxUsableTime(integer);
                break;
            case "max-discount-amount":
                discountCode.setMaxDiscountAmount(integer);
                break;
            case "percent":
                if (integer >= 100) {
                    sendError("Percent should be a number between 0 and 100!!");
                    return;
                }
                discountCode.setPercent(integer);
                break;
        }
        actionCompleted();
    }
}//end EditCodeCommand class


class RemoveCodeCommand extends DiscountCodeCommands {
    private static RemoveCodeCommand command;

    private RemoveCodeCommand() {
        this.name = "remove discount code manager";
    }

    public static RemoveCodeCommand getInstance() {
        if (command != null)
            return command;
        command = new RemoveCodeCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        removeDiscountCode(request.getArrayList().get(0).toLowerCase());
    }

    private void removeDiscountCode(String code) {
        if (!DiscountCode.isThereDiscountWithCode(code)) {
            sendError("There isn't discount code with this code!!");
            return;
        }
        DiscountCode discountCode = DiscountCode.getDiscountById(code);
        discountCode.remove();
    }
}//end RemoveCodeCommand class

class GiveGiftCommand extends DiscountCodeCommands {
    private static GiveGiftCommand command;

    private GiveGiftCommand() {
        this.name = "give gift";
    }

    public static GiveGiftCommand getInstance() {
        if (command != null)
            return command;
        command = new GiveGiftCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstHashMap()))
            return;
        giveGift(request.getFirstInt(), request.getFirstHashMap());
    }

    private void giveGift(int numberOfUser, HashMap<String, String> giftInfo) {
        CreateCodeCommand createCodeCommand = DiscountCodeCommands.getCreateCodeCommand();

        if (createCodeCommand.discountInfoHasError(giftInfo)) {
            return;
        } else if (createCodeCommand.discountDateHasError(giftInfo.get("start-time"), giftInfo.get("finish-time"))) {
            return;
        } else if (createCodeCommand.discountInfoHasWrongIntegers(giftInfo)) {
            return;
        } else if (numberOfUser < 1) {
            sendError("You should at least add this code to 1 customer!!");
            return;
        }
        createCodeCommand.createDiscountCodeAfterChecking(giftInfo, Manager.getCustomersForGift(numberOfUser));
    }

}//end GiveGiftCommand


