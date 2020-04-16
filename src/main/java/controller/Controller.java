package controller;

import model.user.User;

abstract public class Controller {
    protected static  User loggedUser;
    public static void loadingDatabase(){}

    public User getLoggedUser() {
        return loggedUser;
    }
}
