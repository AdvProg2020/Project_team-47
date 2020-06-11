package model.ecxeption.request;

import model.ecxeption.Exception;

public class RequestDoesntExistException extends Exception {
    public RequestDoesntExistException() {
        super("There isn't any request with this id!!");
    }
}
