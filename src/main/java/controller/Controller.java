package controller;

import com.google.gson.Gson;
import controller.login.LoginController;
import controller.off.OffController;
import controller.panels.UserPanelController;
import controller.product.AllProductsController;
import controller.product.ProductController;
import controller.purchase.PurchaseController;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.common.DateException;
import model.others.ShoppingCart;
import model.send.receive.*;
import model.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class Controller {
    protected static User loggedUser;
    private static final ArrayList<Controller> controllers;
    private static final Gson gson = new Gson();

    static {
        ShoppingCart.setLocalShoppingCart(new ShoppingCart());
        controllers = new ArrayList<>();
        initializeControllers();
    }

    protected ArrayList<Command> commands;

    private static void initializeControllers() {
        controllers.add(UserPanelController.getInstance());
        controllers.add(LoginController.getInstance());
        controllers.add(AllProductsController.getInstance());
        controllers.add(OffController.getInstance());
        controllers.add(ProductController.getInstance());
        controllers.add(PurchaseController.getInstance());
    }

    public static ServerMessage process(ClientMessage clientMessage) throws Exception {
        for (Controller controller : controllers)
            if (controller.canProcess(clientMessage.getType()))
                return controller.processRequest(clientMessage);

        throw new CommonException("Can't do this request!!");
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        Controller.loggedUser = loggedUser;
    }

    @SuppressWarnings("unchecked")
    public static ServerMessage sendAnswer(ArrayList arrayList, String type) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        switch (type) {
            case "category" -> serverMessage.setCategoryInfoArrayList(arrayList);
            case "user" -> serverMessage.setUserInfoArrayList(arrayList);
            case "code" -> serverMessage.setDiscountCodeInfoArrayList(arrayList);
            case "off" -> serverMessage.setOffInfoArrayList(arrayList);
            case "request" -> serverMessage.setRequestArrayList(arrayList);
            case "product" -> serverMessage.setProductInfoArrayList(arrayList);
            case "log" -> serverMessage.setLogInfoArrayList(arrayList);
            case "comment" -> serverMessage.setCommentArrayList(arrayList);
            case "sort" -> serverMessage.setStrings(arrayList);
            case "filter" -> serverMessage.setFilters(arrayList);
        }
        return serverMessage;
    }

    public static ServerMessage sendAnswer(double number) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setNumber(number);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(CategoryInfo categoryInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setCategoryInfo(categoryInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(LogInfo logInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setLogInfo(logInfo);
        return serverMessage;

    }

    public static ServerMessage sendAnswer(ProductInfo productInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setProductInfo(productInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(CartInfo cartInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setCartInfo(cartInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(DiscountCodeInfo discountCodeInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setDiscountCodeInfo(discountCodeInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(OffInfo offInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setOffInfo(offInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(RequestInfo requestInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setRequestInfo(requestInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(UserInfo userInfo) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setUserInfo(userInfo);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(String firstString) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setFirstString(firstString);
        return serverMessage;
    }

    public static ServerMessage sendAnswer(String firstString, String secondString) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        serverMessage.setFirstString(firstString);
        serverMessage.setSecondString(secondString);
        return serverMessage;
    }

    public static ServerMessage actionCompleted() {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setType("Successful");
        return serverMessage;
    }

/*    private static void send(String answer) {
        ViewToController.setControllerAnswer(answer);
    }*/

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

    public static Date getDateWithString(String dateString) throws DateException {
        if (!isDateFormatValid(dateString))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            throw new DateException();
        }
    }

    public static Gson getGson() {
        return gson;
    }

    public ServerMessage processRequest(ClientMessage request) throws Exception {
        for (Command command : commands)
            if (command.canDoIt(request.getType()))
                return command.process(request);
        throw new CommonException("Can't do this request!!");
    }

    public boolean canProcess(String request) {
        for (Command command : commands)
            if (command.canDoIt(request))
                return true;

        return false;
    }
}//end Controller Class
