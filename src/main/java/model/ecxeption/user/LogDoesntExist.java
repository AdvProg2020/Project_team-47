package model.ecxeption.user;

import model.ecxeption.Exception;

public class LogDoesntExist extends Exception {
    public LogDoesntExist() {
        super("You don't have any order with this id!!");
    }
}
