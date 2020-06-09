package model.ecxeption.user;

import model.ecxeption.Exception;

public class UserTypeException extends Exception {
    public UserTypeException(String message) {
        super(message);
    }

    public static class NeedManagerException extends UserTypeException {
        public NeedManagerException() {
            super("You should login as a manager to do this!!");
        }
    }

    public static class NeedCustomerException extends UserTypeException {
        public NeedCustomerException() {
            super("You should login as a Customer to do this!!");
        }
    }
    public static class NeedSellerException extends UserTypeException {
        public NeedSellerException() {
            super("You should login as a seller to do this!!");
        }
    }

}
