package model.ecxeption.Bank;

import model.ecxeption.Exception;

public class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }

    public static class IncorrectPasswordException extends BankException{
        public IncorrectPasswordException() {
            super("passwords do not match");
        }
    }

    public static class UnavailableUsernameException extends BankException{
        public UnavailableUsernameException() {
            super("username is not available");
        }
    }

    public static class InvalidReceiptTypeException extends BankException{
        public InvalidReceiptTypeException() {
            super("invalid receipt type");
        }
    }

    public static class InvalidMoneyException extends BankException{
        public InvalidMoneyException() {
            super("invalid money");
        }
    }

    public static class InvalidTokenException extends BankException{
        public InvalidTokenException() {
            super("token is invalid");
        }
    }

    public static class ExpiredTokenException extends BankException{
        public ExpiredTokenException() {
            super("token expired");
        }
    }

    public static class InvalidSourceAccountIdException extends BankException{
        public InvalidSourceAccountIdException() {
            super("source account id is invalid");
        }
    }

    public static class InvalidDestAccountIdException extends BankException{
        public InvalidDestAccountIdException() {
            super("dest account id is invalid");
        }
    }

    public static class SourceAndDestIdSameException extends BankException{
        public SourceAndDestIdSameException() {
            super("equal source and dest account");
        }
    }

    public static class InvalidAccountIdException extends BankException{
        public InvalidAccountIdException() {
            super("invalid account id");
        }
    }

    public static class InvalidCharactersException extends BankException{
        public InvalidCharactersException() {
            super("your input contains invalid characters");
        }
    }

    public static class InvalidParametersException extends BankException {
        public InvalidParametersException() {
            super("invalid parameters passed");
        }
    }

    public static class InvalidInputException extends BankException {
        public InvalidInputException() {
            super("invalid input");
        }
    }

    public static class DatabaseProblemException extends BankException {
        public DatabaseProblemException() {
            super("database error");
        }
    }

    public static class InvalidReceiptIdException extends BankException {
        public InvalidReceiptIdException() {
            super("invalid receipt id");
        }
    }

    public static class PaidReceiptException extends BankException {
        public PaidReceiptException() {
            super("receipt is paid before");
        }
    }

    public static class InsufficientMoneyException extends BankException {
        public InsufficientMoneyException() {
            super("source account does not have enough money");
        }
    }

    public static class InvalidUsernameOrPasswordException extends BankException {
        public InvalidUsernameOrPasswordException() {
            super("invalid username or password");
        }
    }


}
