package controller;

import com.google.gson.Gson;
import model.others.ShoppingCart;
import model.send.receive.*;
import model.user.User;
import view.ViewToController;

import java.util.ArrayList;
import java.util.Random;

abstract public class Controller {
    private static Gson gson = new Gson();
    static User loggedUser;

    static {
        ShoppingCart.setLocalShoppingCart(new ShoppingCart());
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    static void sendError(String errorMessage) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("error");
        serverMessage.setFirstString(errorMessage);
        send((new Gson()).toJson(serverMessage));
    }

    static void sendAnswer(ArrayList arrayList, String type) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        switch (type) {
            case "category":
                serverMessage.setCategoryInfoArrayList(arrayList);
                break;
            case "user":
                serverMessage.setUserInfoArrayList(arrayList);
                break;
            case "code":
                serverMessage.setDiscountCodeInfoArrayList(arrayList);
                break;
            case "off":
                serverMessage.setOffInfoArrayList(arrayList);
                break;
            case "request":
                serverMessage.setRequestArrayList(arrayList);
                break;
            case "product":
                serverMessage.setProductInfoArrayList(arrayList);
                break;
            case "log":
                serverMessage.setLogInfoArrayList(arrayList);
                break;
            case "comment":
                serverMessage.setCommentArrayList(arrayList);
                break;
            case "sort":
            case "filter":
                serverMessage.setStrings(arrayList);
                break;
            default:
                sendError("Wrong ArrayList type.(Server Error!!)");
                return;
        }
        send((new Gson()).toJson(serverMessage));
    }

    static void sendAnswer(double number) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setNumber(number);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(CategoryInfo categoryInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setCategoryInfo(categoryInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(LogInfo logInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setLogInfo(logInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(ProductInfo productInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setProductInfo(productInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(CartInfo cartInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setCartInfo(cartInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(DiscountCodeInfo discountCodeInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setDiscountCodeInfo(discountCodeInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(OffInfo offInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setOffInfo(offInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(RequestInfo requestInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setRequestInfo(requestInfo);
        send(gson.toJson(serverMessage));
    }

    static void sendAnswer(UserInfo userInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setUserInfo(userInfo);
        send((new Gson()).toJson(serverMessage));
    }

    static void sendAnswer(String firstString) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setFirstString(firstString);
        send((new Gson()).toJson(serverMessage));
    }

    static void sendAnswer(String firstString, String secondString) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setFirstString(firstString);
        serverMessage.setSecondString(secondString);
        send(gson.toJson(serverMessage));
    }

    static void actionCompleted() {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("successful");
        send(gson.toJson(serverMessage));
    }

    private static void send(String answer) {
        ViewToController.setControllerAnswer(answer);
    }

    public static String idCreator() {
        //this function will create a random string such as "AB1234"
        Random randomNumber = new Random();
        String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length()))) +
                upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())) +
                randomNumber.nextInt(10000);
    }

    public static Gson getGson() {
        return gson;
    }
}//end Controller Class
