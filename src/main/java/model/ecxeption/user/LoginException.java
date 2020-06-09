package model.ecxeption.user;

import model.ecxeption.Exception;

public class LoginException extends Exception {
    public LoginException() {
        super("There isn't any user with this username and password!!");
    }
}
