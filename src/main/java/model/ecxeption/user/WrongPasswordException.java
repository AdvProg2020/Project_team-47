package model.ecxeption.user;

import model.ecxeption.Exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Password is incorrect!!");
    }
}
