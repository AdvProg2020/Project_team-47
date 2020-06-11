package controller.panels.manager;

import controller.Command;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.request.CantDoRequestException;
import model.ecxeption.user.UserTypeException;
import model.others.request.Request;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import static controller.Controller.actionCompleted;
import static controller.Controller.sendAnswer;
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
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedManagerException {
        return manageRequest(request.getHashMap().get("field"), request.getHashMap().get("direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        String sortField = request.getHashMap().get("sort field");
        String sortDirection = request.getHashMap().get("sort direction");

        if (sortField != null && sortDirection != null) {
            sortField = sortField.toLowerCase();
            sortDirection = sortDirection.toLowerCase();
        }

        if (!checkSort(sortField, sortDirection, "request"))
            throw new InvalidSortException();
    }

    private ServerMessage manageRequest(String sortField, String sortDirection) {
        return sendAnswer(Request.allRequestInfo(sortField, sortDirection), "request");
    }

}//end ShowRequestsCommand Class


class RequestDetailCommand extends ManageRequestsCommands {
    private static RequestDetailCommand command;
    private Request request;

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
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedManagerException, NullFieldException {
        containNullField(request.getHashMap().get("id"));
        return requestDetail();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        this.request = Request.getRequestById(request.getHashMap().get("id"));

    }

    private ServerMessage requestDetail() {
        assert request != null;
        return sendAnswer(request.detail());
    }

}//end RequestDetailCommand Class

class AcceptRequestCommand extends ManageRequestsCommands {
    private static AcceptRequestCommand command;
    private Request request;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("id"));
        checkPrimaryErrors(request);
        acceptRequest(request.getHashMap().get("id"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        this.request = Request.getRequestById(request.getHashMap().get("id"));
        if (!this.request.canDoRequest()) {
            this.request.decline();
            throw new CantDoRequestException();
        }
    }

    private void acceptRequest(String id) {
        request.accept();
    }

}//end AcceptRequestClass Class

class DeclineRequestCommand extends ManageRequestsCommands {
    private static DeclineRequestCommand command;
    private Request request;

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
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("id"));
        checkPrimaryErrors(request);
        declineRequest(request.getHashMap().get("id"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        this.request = Request.getRequestById(request.getHashMap().get("id"));
    }

    private void declineRequest(String id) {
        request.decline();
    }

}//end DeclineRequestClass class
