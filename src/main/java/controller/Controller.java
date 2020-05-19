package controller;

import com.google.gson.Gson;
import controller.login.LoginController;
import controller.off.OffController;
import controller.panels.UserPanelController;
import controller.product.AllProductsController;
import controller.product.ProductController;
import controller.purchase.PurchaseController;
import model.others.ShoppingCart;
import model.send.receive.*;
import model.user.User;
import view.ViewToController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class Controller {
    protected static User loggedUser;
    private static ArrayList<Controller> controllers;
    private static Gson gson = new Gson();

    static {
        ShoppingCart.setLocalShoppingCart(new ShoppingCart());
        controllers = new ArrayList<>();
        initializeControllers();
    }

    private static void initializeControllers() {
        controllers.add(UserPanelController.getInstance());
        controllers.add(LoginController.getInstance());
        controllers.add(AllProductsController.getInstance());
        controllers.add(OffController.getInstance());
        controllers.add(ProductController.getInstance());
        controllers.add(PurchaseController.getInstance());
    }

    public static void process(ClientMessage clientMessage) {
        for (Controller controller : controllers) {
            if (controller.canProcess(clientMessage.getRequest())) {
                controller.processRequest(clientMessage);
            }
        }
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        Controller.loggedUser = loggedUser;
    }

    public static void sendError(String errorMessage) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("error");
        serverMessage.setFirstString(errorMessage);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(ArrayList arrayList, String type) {
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

    public static void sendAnswer(double number) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setNumber(number);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(CategoryInfo categoryInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setCategoryInfo(categoryInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(LogInfo logInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setLogInfo(logInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(ProductInfo productInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setProductInfo(productInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(CartInfo cartInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setCartInfo(cartInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(DiscountCodeInfo discountCodeInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setDiscountCodeInfo(discountCodeInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(OffInfo offInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setOffInfo(offInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(RequestInfo requestInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setRequestInfo(requestInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(UserInfo userInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setUserInfo(userInfo);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(String firstString) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setFirstString(firstString);
        send(gson.toJson(serverMessage));
    }

    public static void sendAnswer(String firstString, String secondString) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setFirstString(firstString);
        serverMessage.setSecondString(secondString);
        send(gson.toJson(serverMessage));
    }

    public static void actionCompleted() {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
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

    public static Date getCurrentTime() {
        return new Date();
    }

    public static boolean isDateFormatValid(String date) {
        String regex = "(\\d{1,2})-(\\d{1,2})-(\\d{4}) (\\d{1,2}):(\\d{1,2})";
        Pattern datePattern = Pattern.compile(regex);
        Matcher dateMatcher = datePattern.matcher(date);
        if (!dateMatcher.find())
            return false;
        int day = Integer.parseInt(dateMatcher.group(1));
        int month = Integer.parseInt(dateMatcher.group(2));
        int year = Integer.parseInt(dateMatcher.group(3));
        int hour = Integer.parseInt(dateMatcher.group(4));
        int minute = Integer.parseInt(dateMatcher.group(5));
        if (day < 1 || day > 31)
            return false;
        else if (month < 1 || month > 12)
            return false;
        else if (year < 1970 || year > 2500)
            return false;
        else if (hour < 0 || hour > 24)
            return false;
        else return minute >= 0 && minute <= 60;
    }

    public static Date getDateWithString(String dateString) {
        if (!isDateFormatValid(dateString))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Gson getGson() {
        return gson;
    }

    public abstract void processRequest(ClientMessage request);

    public abstract boolean canProcess(String request);
}//end Controller Class
