package model.ecxeption.filter;

import model.ecxeption.Exception;

public class FilterNotExistException extends Exception {
    public FilterNotExistException() {
        super("There isn't any filter with this key!!");
    }
}
