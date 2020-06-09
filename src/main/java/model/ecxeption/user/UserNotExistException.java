package model.ecxeption.user;

import model.ecxeption.Exception;

public class UserNotExistException extends Exception {
    public UserNotExistException() {
        super("User not exist!!");
    }
}
