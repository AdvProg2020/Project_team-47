package model.ecxeption.user;

import model.ecxeption.Exception;

public class RegisterException extends Exception {
    public RegisterException(String message) {
        super(message);
    }

    public static class PhoneNumberUsedException extends RegisterException {
        public PhoneNumberUsedException() {
            super("There is a user with this phone number already!!");
        }
    }

    public static class EmailUsedException extends RegisterException {
        public EmailUsedException() {
            super("There is a user with this email already!!");
        }
    }

    public static class PhoneNumberNotValidException extends RegisterException {
        public PhoneNumberNotValidException() {
            super("Phone number isn't valid!!");
        }
    }
    public static  class EmailNotValidException extends RegisterException {
        public EmailNotValidException() {
            super("Email isn't valid!!");
        }
    }

    public static class UsernameNotValidException extends RegisterException {
        public UsernameNotValidException() {
            super("Username isn't valid!!\nit should contains " +
                    "more than five letters and only letters and numbers");
        }
    }

    public static class UsernameUsedException extends RegisterException {
        public UsernameUsedException() {
            super("There is user with this username already!!");
        }
    }
}
