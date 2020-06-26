package model.ecxeption.common;

import model.ecxeption.Exception;

public class OffDoesntExistException extends Exception {
    public OffDoesntExistException() {
        super("Off doesn't exist!!");
    }
}
