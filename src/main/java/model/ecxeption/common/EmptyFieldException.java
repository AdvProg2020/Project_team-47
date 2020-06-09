package model.ecxeption.common;

import model.ecxeption.Exception;

public class EmptyFieldException extends Exception {
    public EmptyFieldException(String field) {
        super(field + "'s field shouldn't be empty!!");
    }
}
