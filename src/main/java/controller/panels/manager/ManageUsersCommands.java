package controller.panels.manager;

import controller.Command;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.user.PasswordNotValidException;
import model.ecxeption.user.RegisterException;
import model.ecxeption.user.UserTypeException;
import model.ecxeption.user.manager.RemoveYourselfException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Manager;
import model.user.User;

import java.util.HashMap;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkRegisterInfoKey;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class ManageUsersCommands extends Command {

    public static ShowUsersCommand getShowUsersCommand() {
        return ShowUsersCommand.getInstance();
    }

    public static DeleteUserCommand getDeleteUserCommand() {
        return DeleteUserCommand.getInstance();
    }

    public static CreateManagerCommand getCreateManagerCommand() {
        return CreateManagerCommand.getInstance();
    }

    public static ViewUserCommand getViewUserCommand() {
        return ViewUserCommand.getInstance();
    }
}


class ShowUsersCommand extends ManageUsersCommands {
    private static ShowUsersCommand command;

    private ShowUsersCommand() {
        this.name = "manage users";
    }

    public static ShowUsersCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowUsersCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedManagerException, NullFieldException, InvalidSortException {
        containNullField(request.getHashMap());
        return manageUsers(request.getHashMap().get("field"), request.getHashMap().get("direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage manageUsers(String field, String direction) throws InvalidSortException {
        if (field != null && direction != null) {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
        }

        if (!checkSort(field, direction, "user")) {
            throw new InvalidSortException();
        }

        return sendAnswer(User.getAllUsers(field, direction), "user");
    }

}//end ShowUsersCommand Class


class DeleteUserCommand extends ManageUsersCommands {
    private static DeleteUserCommand command;
    private User user;

    private DeleteUserCommand() {
        this.name = "delete user";
    }

    public static DeleteUserCommand getInstance() {
        if (command != null)
            return command;
        command = new DeleteUserCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("username"));
        checkPrimaryErrors(request);
        deleteUser();
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        user = User.getUserByUsername(request.getHashMap().get("username"));
        if (user == getLoggedUser())
            throw new RemoveYourselfException();
    }

    public void deleteUser() {
        assert user != null;
        user.deleteUser();
    }

}//end DeleteUserCommand Class


class CreateManagerCommand extends ManageUsersCommands {
    private static CreateManagerCommand command;
    private byte[] avatar;

    private CreateManagerCommand() {
        this.name = "create manager profile";
    }


    public static CreateManagerCommand getInstance() {
        if (command != null)
            return command;
        command = new CreateManagerCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        avatar = request.getFile();
        containNullField(request);
        checkPrimaryErrors(request);
        createManager(request.getHashMap());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        checkRegisterInfoKey(request.getHashMap());

        if (!User.isUsernameValid(request.getHashMap().get("username"))) {
            throw new RegisterException.UsernameNotValidException();
        } else if (User.doesUsernameUsed(request.getHashMap().get("username"))) {
            throw new RegisterException.UsernameUsedException();
        } else if (!User.isEmailValid(request.getHashMap().get("email"))) {
            throw new RegisterException.EmailNotValidException();
        } else if (!User.isPhoneValid(request.getHashMap().get("phone-number"))) {
            throw new RegisterException.PhoneNumberNotValidException();
        } else if (User.isThereUserWithEmail(request.getHashMap().get("email"))) {
            throw new RegisterException.EmailUsedException();
        } else if (User.isThereUserWithPhone(request.getHashMap().get("phone-number"))) {
            throw new RegisterException.PhoneNumberUsedException();
        } else if (!User.isPasswordValid(request.getHashMap().get("password"))) {
            throw new PasswordNotValidException();
        }
    }

    private void createManager(HashMap<String, String> managerInformationHashMap) {
        registerManager(managerInformationHashMap);
    }

    private void registerManager(HashMap<String, String> userInformation) {
        User newUser = new Manager(userInformation, avatar);
        newUser.emailVerification();
        newUser.confirmEmail();
    }
}//end CreateManagerCommand class


class ViewUserCommand extends ManageUsersCommands {
    private static ViewUserCommand command;
    private User user;

    private ViewUserCommand() {
        this.name = "view user";
    }


    public static ViewUserCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewUserCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedManagerException, NullFieldException {
        containNullField(request.getHashMap().get("username"));
        return viewUser(request.getHashMap().get("username"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        user = User.getUserByUsername(request.getHashMap().get("username"));
    }


    private ServerMessage viewUser(String username) {
        assert user != null;
        return sendAnswer(user.userInfoForSending());
    }

}//end ViewUserCommand class







