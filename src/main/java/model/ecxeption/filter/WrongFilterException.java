package model.ecxeption.filter;

import model.ecxeption.Exception;

public class WrongFilterException extends Exception {
    public WrongFilterException() {
        super("Filter key doesn't exist!!");
    }
}
