package model.ecxeption.user.manager;

import model.ecxeption.Exception;

public class RemoveYourselfException extends Exception {
    public RemoveYourselfException() {
        super("You can't remove yourself!!");
    }
}
