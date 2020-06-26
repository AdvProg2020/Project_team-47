package model.ecxeption.user;

import model.ecxeption.Exception;

public class ManagerExistenceException extends Exception {
    public ManagerExistenceException(String message) {
        super(message);
    }

    public static class ManagerExist extends ManagerExistenceException {
        public ManagerExist() {
            super("There is manager and you can't register as a manager!!");
        }
    }

    public static class ManagerDoesntExist extends ManagerExistenceException {
        public ManagerDoesntExist() {
            super("There isn't a manager and you should register as a manager!!");
        }
    }

}
