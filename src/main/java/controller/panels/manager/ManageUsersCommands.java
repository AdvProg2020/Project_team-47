package controller.panels.manager;

import controller.Command;
import controller.Error;
import model.send.receive.ClientMessage;
import model.user.Manager;
import model.user.User;

import java.util.ArrayList;
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        ArrayList<String> reqInfo = getReqInfo(request);
        manageUsers(reqInfo.get(0), reqInfo.get(1));
    }

    private void manageUsers(String field, String direction) {
        if (field != null && direction != null) {
            field = field.toLowerCase();
            direction = direction.toLowerCase();
        }

        if (!checkSort(field, direction, "user")) {
            sendError(Error.CANT_SORT.getError());
            return;
        }

        sendAnswer(User.getAllUsers(field, direction), "user");
    }

}//end ShowUsersCommand Class


class DeleteUserCommand extends ManageUsersCommands {
    private static DeleteUserCommand command;

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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        deleteUser(request.getArrayList().get(0));
    }

    public void deleteUser(String username) {
        if (!User.isThereUserWithUsername(username)) {
            sendError(Error.USER_NOT_EXIST.getError());
            return;
        }
        User user = User.getUserByUsername(username);
        if (user == getLoggedUser()) {
            sendError(Error.REMOVE_YOURSELF.getError());
            return;
        }
        assert user != null;
        user.deleteUser();
        actionCompleted();
    }

}//end DeleteUserCommand Class


class CreateManagerCommand extends ManageUsersCommands {
    private static CreateManagerCommand command;

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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstHashMap()))
            return;
        createManager(request.getFirstHashMap());
    }

    private void createManager(HashMap<String, String> managerInformationHashMap) {

        managerInformationHashMap.put("type", "manager");
        if (!checkRegisterInfoKey(managerInformationHashMap)) {
            sendError("Not enough information!!");
        } else {
            registerManager(managerInformationHashMap);
            actionCompleted();
        }
    }

    private void registerManager(HashMap<String, String> userInformation) {
        User newUser;
        newUser = new Manager(userInformation);
        newUser.emailVerification();
        actionCompleted();
    }
}//end CreateManagerCommand class


class ViewUserCommand extends ManageUsersCommands {
    private static ViewUserCommand command;

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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        viewUser(request.getArrayList().get(0));
    }


    private void viewUser(String username) {
        if (!User.isThereUserWithUsername(username)) {
            sendError("There isn't any account with this username!!");
        } else {
            User user = User.getUserByUsername(username);
            assert user != null;
            sendAnswer(user.userInfoForSending());
        }
    }

}//end ViewUserCommand class







