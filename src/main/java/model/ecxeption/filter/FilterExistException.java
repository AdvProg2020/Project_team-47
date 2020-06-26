package model.ecxeption.filter;

import model.ecxeption.Exception;

public class FilterExistException extends Exception {
    public FilterExistException() {
        super("You are already using this filter!!");
    }
}
