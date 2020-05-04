package controller;

import model.others.ShoppingCart;
import model.user.User;

import java.util.Random;

abstract public class Controller {
    static User loggedUser;

    static {
        ShoppingCart.setLocalShoppingCart(new ShoppingCart());
    }

    public static void loadingDatabase() {
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    static void sendError(String errorMessage) {
    }

    static void sendAnswer(String message) {
    }

    public static String idCreator() {
        StringBuilder id = new StringBuilder();
        Random randomNumber = new Random();
        String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        id.append(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())));
        id.append(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())));
        id.append(randomNumber.nextInt(10000));
        return id.toString();
    }
}
