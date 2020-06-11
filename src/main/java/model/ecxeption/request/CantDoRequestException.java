package model.ecxeption.request;

import model.ecxeption.Exception;

public class CantDoRequestException extends Exception {
    public CantDoRequestException() {
        super("This request can't be accept now due to some change in data!!");
    }
}
