package controller.panels;

import controller.Command;
import model.category.Category;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.EmptyFieldException;
import model.ecxeption.common.NotEnoughInformation;
import model.ecxeption.user.NeedLoginException;
import model.ecxeption.user.PasswordNotValidException;
import model.ecxeption.user.RegisterException;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Seller;
import model.user.User;

import java.util.HashMap;
import java.util.regex.Pattern;

import static controller.Controller.*;

public abstract class UserPanelCommands extends Command {

    public static ViewPersonalInfoCommand getViewPersonalInfoCommand() {
        return ViewPersonalInfoCommand.getInstance();
    }

    public static EditFieldCommand getEditFieldCommand() {
        return EditFieldCommand.getInstance();
    }

    public static boolean checkSort(String field, String direction, String itemToSort) {
        //this function will check that items could sort by the given field and direction

        if (field == null && direction == null) {
            return true;
        } else if (field == null || direction == null) {
            return false;
        } else if (field.isEmpty() && direction.isEmpty()) {
            return true;
        } else {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
            if (!Pattern.matches("(descending|ascending)", direction)) {
                return false;
            }
        }


        return switch (itemToSort) {
            case "log" -> field.equals("money");
            case "request" -> Pattern.matches("(apply-date|sender-username)", field);
            case "user" -> Pattern.matches("(first-name|last-name|username)", field);
            case "off", "discount-code" -> Pattern.matches("(start-time|finish-time|percent)", field);
            case "product" -> Pattern.matches("(name|score|seen-time|price)", field);
            case "category" -> field.equals("name");
            default -> false;
        };
    }

    public static void checkRegisterInfoKey(HashMap<String, String> registerInfo) throws NotEnoughInformation {
        //this function will check that if registerInfo HashMap contains all key that should have or not

        String[] registerKey = {"username", "password", "first-name", "last-name", "email", "phone-number", "type"};
        for (String key : registerKey) {
            if (!registerInfo.containsKey(key))
                throw new NotEnoughInformation();
        }

        String[] sellerKey = {"company-info", "company-name"};
        if (registerInfo.get("type").equals("seller")) {
            for (String key : sellerKey) {
                if (!registerInfo.containsKey(key))
                    throw new NotEnoughInformation();
            }
        }
    }

    public static boolean isThereProperty(Category category, String property) {
        SpecialProperty temp = new SpecialProperty(property);
        return category.getSpecialProperties().contains(temp);
    }
}


class ViewPersonalInfoCommand extends UserPanelCommands {
    private static ViewPersonalInfoCommand command;


    private ViewPersonalInfoCommand() {
        this.name = "view personal info";
    }

    public static ViewPersonalInfoCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewPersonalInfoCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NeedLoginException {
        return personalInfo();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }


    public ServerMessage personalInfo() {
        return sendAnswer(getLoggedUser().userInfoForSending());
    }

}//end ViewPersonalInfoCommand Class


class EditFieldCommand extends UserPanelCommands {
    private static EditFieldCommand command;

    private EditFieldCommand() {
        this.name = "edit personal info";
    }

    public static EditFieldCommand getInstance() {
        if (command != null)
            return command;
        command = new EditFieldCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        HashMap<String, String> reqInfo = getReqInfo(request);
        containNullField(reqInfo, reqInfo.get("field"), reqInfo.get("new value"));
        edit(reqInfo.get("field"), reqInfo.get("new value"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
    }


    public void edit(String field, String newValue) throws EmptyFieldException, CommonException, RegisterException, PasswordNotValidException {
        if (newValue.length() == 0) {
            throw new EmptyFieldException("New value");
        }
        switch (field) {
            case "first-name" -> getLoggedUser().setFirstName(newValue);
            case "last-name" -> getLoggedUser().setLastName(newValue);
            case "phone-number" -> editPhoneNumber(newValue);
            case "password" -> editPassword(newValue);
            case "company-name" -> {
                if (getLoggedUser() instanceof Seller)
                    editCompanyName(newValue);
                else
                    wrongEditFiled();
            }
            case "company-info" -> {
                if (getLoggedUser() instanceof Seller)
                    editCompanyInfo(newValue);
                else
                    wrongEditFiled();
            }

            default -> wrongEditFiled();

        }
        getLoggedUser().updateDatabase().update();
    }


    private void editCompanyName(String newName) {
        ((Seller) getLoggedUser()).setCompanyName(newName);
    }

    private void editCompanyInfo(String newInfo) {
        ((Seller) getLoggedUser()).setCompanyInfo(newInfo);
    }

    private void editPhoneNumber(String newPhoneNumber) throws RegisterException {
        if (!User.isPhoneValid(newPhoneNumber)) {
            throw new RegisterException.PhoneNumberNotValidException();
        }
        String lastPhoneNumber = getLoggedUser().getPhoneNumber();
        getLoggedUser().setPhoneNumber("");
        if (User.isThereUserWithPhone(newPhoneNumber)) {
            getLoggedUser().setPhoneNumber(lastPhoneNumber);
            throw new RegisterException.PhoneNumberUsedException();
        } else {
            getLoggedUser().setPhoneNumber(newPhoneNumber);
        }
    }

    private void editPassword(String newPassword) throws PasswordNotValidException {
        if (!User.isPasswordValid(newPassword)) {
            throw new PasswordNotValidException();
        } else {
            getLoggedUser().setPassword(newPassword);
        }
    }

    private void wrongEditFiled() throws CommonException {
        throw new CommonException("Error");
    }

}



