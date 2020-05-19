package controller;

public enum Error {
    ERROR("Error!!"),
    NEED_LOGIN("Need to login first!!"),
    USERNAME_NOT_VALID("Username isn't valid!!"),
    PASSWORD_NOT_VALID("Password isn't valid!!"),
    USER_PASS_NOT_EXIST("There isn't any user with this username and password!!"),
    USER_EMAIL_NOT_EXIST("There isn't any user with this email and username!!"),
    USER_NOT_EXIST("There isn't any user with this username!!"),
    INCORRECT_CODE("Incorrect code!!"),
    UNVALID_PHONE("Phone number isn't valid!!"),
    REPEATED_PHONE("This phone used by another user!!"),
    REPEATED_EMAIL("This email used by another user!!"),
    NEED_MANGER("You should login as a manager to do this!!"),
    NEED_SELLER("You should login as a seller to do this!!"),
    NEED_CUSTOMER("You should login as a customer to do this!!"),
    NULL_FIELD("Null field in view message!!"),
    CANT_SORT("Can't sort with this field and direction!!"),
    REMOVE_YOURSELF("You can't remove yourself!!");

    private String error;

    Error(String path) {
        this.error = path;
    }

    public String getError() {
        return error;
    }
}
