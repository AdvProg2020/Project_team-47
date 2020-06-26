package model.ecxeption.purchase;

import model.ecxeption.Exception;

public class CodeException extends Exception {
    public CodeException(String message) {
        super(message);
    }

    public static class DontHaveCode extends CodeException {
        public DontHaveCode() {
            super("You don't have this discount code!!");
        }
    }

    public static class CantUseCodeException extends CodeException {
        public CantUseCodeException() {
            super("Sorry you can't use this code!!");
        }
    }

}
