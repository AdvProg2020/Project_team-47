package controller.panels.manager;

import controller.Command;
import controller.Controller;
import model.discount.DiscountCode;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.*;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.user.UserNotExistException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;
import model.user.Manager;
import model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;
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
}

class CreateCodeCommand extends DiscountCodeCommands {
    private static CreateCodeCommand command;
    private Date startDate;
    private Date finishDate;
    private int percent;
    private int maxUsableTime;
    private int maxAmount;

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
    public ServerMessage process(ClientMessage request) throws NullFieldException, UserNotExistException {
        containNullField(request.getHashMap(), request.getArrayList());
        return createDiscountCode(request.getHashMap(), request.getArrayList());
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        HashMap<String, String> discountInfo = getReqInfo(request);
        ArrayList<String> usernames = request.getArrayList();
        checkDiscountInfoKeys(discountInfo);
        checkDiscountInfoUsernames(usernames);
        checkDiscountInfoDate(discountInfo.get("start-time"), discountInfo.get("finish-time"));
        checkDiscountInfoIntegers(discountInfo);
    }


    private ServerMessage createDiscountCode(HashMap<String, String> discountInfo, ArrayList<String> usernames) throws UserNotExistException {

        ArrayList<Customer> users = new ArrayList<>();
        for (String username : usernames) {
            Customer user = (Customer) User.getUserByUsername(username);
            users.add(user);
        }

        return createDiscountCodeAfterChecking(discountInfo, users);
    }

    void checkDiscountInfoKeys(HashMap<String, String> discountInfo) throws NotEnoughInformation {
        //checking that discount info HashMap contains all required key
        String[] discountKey = {"start-time", "finish-time", "max-usable-time", "max-discount-amount", "percent"};
        for (String key : discountKey) {
            if (!discountInfo.containsKey(key)) {
                throw new NotEnoughInformation();
            }
        }
    }


    private void checkDiscountInfoUsernames(ArrayList<String> usernames) throws UserNotExistException {
        //check that users who could use code exists and check start and finish time
        User.isThereCustomersWithUsername(usernames);
    }


    void checkDiscountInfoDate(String start, String finish) throws DateException {
        //this function will check that discount starting and finishing time entered correctly

        if (!(Controller.isDateFormatValid(start) && Controller.isDateFormatValid(finish)))
            throw new DateException();

        startDate = Controller.getDateWithString(start);
        finishDate = Controller.getDateWithString(finish);

        assert startDate != null;
        assert finishDate != null;
        if (startDate.before(Controller.getCurrentTime()))
            throw new DateException("Can't set start time to this value!!");
        else if (finishDate.before(Controller.getCurrentTime()))
            throw new DateException("Can't set finish time to this value!!");
        else if (!startDate.before(finishDate))
            throw new DateException("Start time should be before finish time!!");
    }


    void checkDiscountInfoIntegers(HashMap<String, String> discountInfo) throws NumberException, InvalidPercentException {
        //check that integer value entered correctly
        try {
            percent = Integer.parseInt(discountInfo.get("percent"));
            maxAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
            maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));

            if (percent >= 100 || percent <= 0) {
                throw new InvalidPercentException();
            } else if (maxAmount <= 0) {
                throw new NumberException();
            } else if (maxUsableTime <= 0) {
                throw new NumberException();
            }
        } catch (NumberFormatException e) {
            throw new NumberException();
        }
    }


    ServerMessage createDiscountCodeAfterChecking(HashMap<String, String> discountInfo, ArrayList<Customer> users) {
        percent = Integer.parseInt(discountInfo.get("percent"));
        maxAmount = Integer.parseInt(discountInfo.get("max-discount-amount"));
        maxUsableTime = Integer.parseInt(discountInfo.get("max-usable-time"));

        DiscountCode discountCode = new DiscountCode(maxAmount, maxUsableTime);
        discountCode.setStartTime(startDate);
        discountCode.setFinishTime(finishDate);
        discountCode.setUsersAbleToUse(users);
        discountCode.setPercent(percent);
        discountCode.updateDatabase();
        for (Customer user : users) {
            user.addDiscountCode(discountCode);
            user.updateDatabase().update();
        }

        return sendAnswer(discountCode.getDiscountCode());
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
    public ServerMessage process(ClientMessage request) throws InvalidSortException {
        return viewAllDiscountCodes(request.getHashMap().get("field"), request.getHashMap().get("direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewAllDiscountCodes(String field, String direction) throws InvalidSortException {
        if (field != null && direction != null) {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
        }

        if (!checkSort(field, direction, "discount-code")) {
            throw new InvalidSortException();
        } else
            return sendAnswer(DiscountCode.getAllDiscountCodeInfo(field, direction), "code");
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, CommonException {
        containNullField(request.getHashMap().get("code"));
        return viewDiscountCode(request.getHashMap().get("code").toLowerCase());
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewDiscountCode(String code) throws CommonException {
        return sendAnswer(DiscountCode.getDiscountById(code).discountCodeInfo());
    }
}//end ViewCodeCommands class


class EditCodeCommand extends DiscountCodeCommands {
    private static EditCodeCommand command;
    private DiscountCode code;

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
    public ServerMessage process(ClientMessage request) throws NullFieldException, CommonException, DateException, NumberException, InvalidPercentException {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo.get("code"), reqInfo.get("field"), reqInfo.get("new value"));
        editDiscountCode(reqInfo.get("code"), reqInfo.get("field"), reqInfo.get("new value"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        code = DiscountCode.getDiscountById(request.getHashMap().get("code"));
    }

    public void editDiscountCode(String code, String field, String newValue) throws CommonException, DateException, NumberException, InvalidPercentException {
        switch (field) {
            case "start-time" -> editCodeTime(this.code, newValue, "Starting time");
            case "finish-time" -> editCodeTime(this.code, newValue, "Finishing time");
            case "max-discount-amount" -> editCodeIntegersValues(this.code, "max-discount-amount", newValue);
            case "percent" -> editCodeIntegersValues(this.code, "percent", newValue);
            case "max-usable-time" -> editCodeIntegersValues(this.code, "max-usable-time", newValue);
            default -> throw new CommonException("Wrong field!!");
        }
        this.code.updateDatabase();
    }

    private void editCodeTime(DiscountCode discountCode, String timeString, String type) throws DateException, CommonException {
        if (!Controller.isDateFormatValid(timeString)) throw new DateException();

        Date time = Controller.getDateWithString(timeString);
        assert time != null;

        if (time.before(Controller.getCurrentTime())) throw new CommonException(type + " can't change to this value!!");

        switch (type) {
            case "Starting time" -> discountCode.setStartTime(time);
            case "Finishing time" -> discountCode.setFinishTime(time);
        }
    }

    private void editCodeIntegersValues(DiscountCode discountCode, String type, String integerString) throws NumberException, InvalidPercentException {
        int integer;
        try {
            integer = Integer.parseInt(integerString);
        } catch (NumberFormatException e) {
            throw new NumberException();
        }

        if (integer <= 0) throw new NumberException();

        switch (type) {
            case "max-usable-time" -> discountCode.setMaxUsableTime(integer);
            case "max-discount-amount" -> discountCode.setMaxDiscountAmount(integer);
            case "percent" -> {
                if (integer >= 100) throw new InvalidPercentException();
                discountCode.setPercent(integer);
            }
        }
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
    public ServerMessage process(ClientMessage request) throws NullFieldException, CommonException {
        containNullField(request.getHashMap().get("code"));
        removeDiscountCode(request.getHashMap().get("code").toLowerCase());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void removeDiscountCode(String code) throws CommonException {
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
    public ServerMessage process(ClientMessage request) throws NotEnoughInformation, CommonException, InvalidPercentException, DateException, NumberException {
        return giveGift(request.getHashMap());
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage giveGift(HashMap<String, String> giftInfo) throws NotEnoughInformation, NumberException, DateException, InvalidPercentException, CommonException {
        if (!giftInfo.containsKey("number of user"))
            throw new NotEnoughInformation();

        int numberOfUser;

        try {
            numberOfUser = Integer.parseInt(giftInfo.get("number of user"));
        } catch (NumberFormatException e) {
            throw new NumberException();
        }

        if (numberOfUser < 1) throw new CommonException("You should at least add this code 1 customer!!");

        CreateCodeCommand createCodeCommand = DiscountCodeCommands.getCreateCodeCommand();

        createCodeCommand.checkDiscountInfoKeys(giftInfo);
        createCodeCommand.checkDiscountInfoDate(giftInfo.get("start-time"), giftInfo.get("finish-time"));
        createCodeCommand.checkDiscountInfoIntegers(giftInfo);

        return createCodeCommand.createDiscountCodeAfterChecking(giftInfo, Manager.getCustomersForGift(numberOfUser));
    }

}//end GiveGiftCommand


