package controller;

import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.user.NeedLoginException;
import model.ecxeption.user.UserTypeException;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static controller.Controller.loggedUser;

public abstract class Command {
    protected String name;

    protected void containNullField(Object firstObj) throws NullFieldException {
        if (firstObj == null)
            throw new NullFieldException();
    }

    protected void containNullField(Object firstObj, Object secondObj) throws NullFieldException {
        if (firstObj == null || secondObj == null)
            throw new NullFieldException();
    }

    protected void containNullField(Object firstObj, Object secondObj, Object thirdObj) throws NullFieldException {
        if (firstObj == null || secondObj == null || thirdObj == null)
            throw new NullFieldException();
    }

    public boolean canDoIt(String requestType) {
        return Pattern.matches(name, requestType);
    }

    public abstract ServerMessage process(ClientMessage request) throws Exception;

    public abstract void checkPrimaryErrors(ClientMessage request) throws Exception;

    protected void shouldLoggedIn() throws NeedLoginException {
        if(loggedUser==null)
            throw new NeedLoginException();
    }

    protected void shouldBeManager() throws UserTypeException.NeedManagerException {
        if(!(loggedUser instanceof Manager))
            throw new UserTypeException.NeedManagerException();
    }

    protected void shouldBeCustomer() throws UserTypeException.NeedCustomerException {
        if(!(loggedUser instanceof Customer))
            throw new UserTypeException.NeedCustomerException();
    }

    protected void shouldBeSeller() throws UserTypeException.NeedSellerException {
        if(!(loggedUser instanceof Seller))
            throw new UserTypeException.NeedSellerException();
    }

    protected HashMap<String,String> getReqInfo(ClientMessage request) {
        return request.getFirstHashMap();
    }
}
