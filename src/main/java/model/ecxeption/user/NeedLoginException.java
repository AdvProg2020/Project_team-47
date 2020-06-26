package model.ecxeption.user;

import model.ecxeption.Exception;

public class NeedLoginException extends Exception {

    public NeedLoginException() {
        super("Need to login first!!");
    }
}
