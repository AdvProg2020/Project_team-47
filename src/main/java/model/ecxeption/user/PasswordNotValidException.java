package model.ecxeption.user;

import model.ecxeption.Exception;

public class PasswordNotValidException extends Exception {
    public PasswordNotValidException() {
        super("Password isn't valid!!");
    }
}
