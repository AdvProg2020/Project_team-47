package model.ecxeption.filter;

import model.ecxeption.Exception;

public class InvalidSortException extends Exception {
    public InvalidSortException() {
        super("Can't sort with this field and direction!!");
    }
}
