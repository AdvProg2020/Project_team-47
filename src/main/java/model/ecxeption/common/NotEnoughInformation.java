package model.ecxeption.common;

import model.ecxeption.Exception;

public class NotEnoughInformation extends Exception {
    public NotEnoughInformation() {
        super("Not enough information!!");
    }
}
