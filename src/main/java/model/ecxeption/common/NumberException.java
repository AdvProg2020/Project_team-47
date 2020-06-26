package model.ecxeption.common;

import model.ecxeption.Exception;

public class NumberException extends Exception {
    public NumberException() {
        super("Enter valid number!!");
    }

    public NumberException(String message) {
        super(message);
    }
}
