package controller.panels.manager;

import controller.Command;
import controller.Error;
import model.others.request.Request;
import model.send.receive.ClientMessage;
import model.user.Manager;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class ManageRequestsCommands extends Command {
    public static ShowRequestsCommand getShowRequestsCommand() {
        return ShowRequestsCommand.getInstance();
    }

    public static RequestDetailCommand getRequestDetailCommand() {
        return RequestDetailCommand.getInstance();
    }

    public static AcceptRequestCommand getAcceptRequestCommand() {
        return AcceptRequestCommand.getInstance();
    }

    public static DeclineRequestCommand getDeclineRequestCommand() {
        return DeclineRequestCommand.getInstance();
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


class ShowRequestsCommand extends ManageRequestsCommands {
    private static ShowRequestsCommand command;

    private ShowRequestsCommand() {
        this.name = "manage requests";
    }

    public static ShowRequestsCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowRequestsCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        manageRequest(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void manageRequest(String sortField, String sortDirection) {
        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }

        if (!checkSort(sortField, sortDirection, "request")) {
            sendError("Can't sort with this field and direction!!");
        } else
            sendAnswer(Request.allRequestInfo(sortField, sortDirection), "request");
    }

}//end ShowRequestsCommand Class


class RequestDetailCommand extends ManageRequestsCommands {
    private static RequestDetailCommand command;

    private RequestDetailCommand() {
        this.name = "request details";
    }

    public static RequestDetailCommand getInstance() {
        if (command != null)
            return command;
        command = new RequestDetailCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        requestDetail(request.getArrayList().get(0).toLowerCase());
    }

    private void requestDetail(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        }
        Request request = Request.getRequestById(id);
        assert request != null;
        sendAnswer(request.detail());
    }

}//end RequestDetailCommand Class

class AcceptRequestCommand extends ManageRequestsCommands {
    private static AcceptRequestCommand command;

    private AcceptRequestCommand() {
        this.name = "accept request";
    }

    public static AcceptRequestCommand getInstance() {
        if (command != null)
            return command;
        command = new AcceptRequestCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        acceptRequest(request.getArrayList().get(0));
    }

    private void acceptRequest(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        } else if (!Request.canDoRequest(id)) {
            Request.declineNewRequest(id);
            sendError("This request can't be accept now due to some change in data!!");
            return;
        }
        Request.acceptNewRequest(id);
        actionCompleted();
    }

}//end AcceptRequestClass Class

class DeclineRequestCommand extends ManageRequestsCommands {
    private static DeclineRequestCommand command;

    private DeclineRequestCommand() {
        this.name = "decline request";
    }

    public static DeclineRequestCommand getInstance() {
        if (command != null)
            return command;
        command = new DeclineRequestCommand();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getArrayList().get(0)))
            return;
        declineRequest(request.getArrayList().get(0));
    }

    private void declineRequest(String id) {
        if (!Request.isThereRequestWithId(id)) {
            sendError("There isn't any request with this id!!");
            return;
        }
        Request.declineNewRequest(id);
        actionCompleted();
    }

}//end DeclineRequestClass class
