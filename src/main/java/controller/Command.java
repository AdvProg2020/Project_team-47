package controller;

import model.send.receive.ClientMessage;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static controller.Controller.sendError;

public abstract class Command {
    protected String name;

    protected boolean containNullField(Object firstObj) {
        if (firstObj != null)
            return false;
        sendError(Error.NULL_FIELD.getError());
        return true;
    }

    protected boolean containNullField(Object firstObj, Object secondObj) {
        if (firstObj != null && secondObj != null)
            return false;
        sendError(Error.NULL_FIELD.getError());
        return true;
    }

    protected boolean containNullField(Object firstObj, Object secondObj, Object thirdObj) {
        if (firstObj != null && secondObj != null & thirdObj != null)
            return false;
        sendError(Error.NULL_FIELD.getError());
        return true;
    }

    public boolean canDoIt(String requestType) {
        return Pattern.matches(name, requestType);
    }

    public abstract void process(ClientMessage request);

    protected ArrayList<String> getReqInfo(ClientMessage request) {
        return request.getArrayList();
    }
}
