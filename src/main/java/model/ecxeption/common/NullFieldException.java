package model.ecxeption.common;

import model.ecxeption.Exception;

public class NullFieldException extends Exception {
    public NullFieldException() {
        super("Null field in view message!!");
    }
}
