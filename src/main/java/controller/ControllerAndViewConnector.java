package controller;

import com.google.gson.Gson;
import model.send.receive.ClientMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerAndViewConnector {
    private static String clientMessage;
    private static String serverAnswer;
    private static HashMap<String, String> commandRegexHashMap;

    public static String getClientMessage() {
        return clientMessage;
    }

    public static String getServerAnswer() {
        return serverAnswer;
    }

    public static void setClientMessage(String clientMessage) {
        ControllerAndViewConnector.clientMessage = clientMessage;
    }

    public static void setServerAnswer(String serverAnswer) {
        ControllerAndViewConnector.serverAnswer = serverAnswer;
    }

    public static void sendAnswer(String type, String answer){

    }


    public static String getAnswer() {
        //todo
        return serverAnswer;
    }

    public static void commandProcess(){
        ClientMessage message = new Gson().fromJson(clientMessage, ClientMessage.class);
        String commandContext = message.getMessageContext();
        ArrayList<String> messageInputs = message.getMessageInputs();
        switch (commandContext) {
            case "create account" :

                break;
            case "login" :

                break;
            case "view personal info" :
                UserPanelController.personalInfo();
                break;
            case "edit personal info" :
                break;
            case "manage users" :
                ManagerPanelController.manageUsers(messageInputs.get(0), messageInputs.get(1));
                break;
            case "view user" :
                ManagerPanelController.viewUser(messageInputs.get(0));
                break;
            case "delete user" :
                ManagerPanelController.deleteUser(messageInputs.get(0));
                break;
            case "create manager profile" :

                break;
            case "manage all products" :
                ManagerPanelController.
                        manageAllProducts(messageInputs.get(0), messageInputs.get(1));
                break;
            case "remove product manager" :
                ManagerPanelController.removeProduct(messageInputs.get(0));
                break;
            case "create discount code" :

                break;
            case "view discount codes manager" :
                ManagerPanelController.
                        viewAllDiscountCodes(messageInputs.get(0), messageInputs.get(1));
                break;
            case "view discount code" :
                ManagerPanelController.viewDiscountCode(messageInputs.get(0));
                break;
            case "edit discount code" :
                ManagerPanelController.editDiscountCode(messageInputs.get(0),
                        messageInputs.get(1), messageInputs.get(2));
                break;
            case "remove discount code" :
                ManagerPanelController.removeDiscountCode(messageInputs.get(0));
                break;
            case "manage requests" :
                ManagerPanelController.manageRequest(messageInputs.get(0), messageInputs.get(1));
                break;
            case "request details" :
                ManagerPanelController.requestDetail(messageInputs.get(0));
                break;
            case "accept request" :
                ManagerPanelController.acceptRequest(messageInputs.get(0));
                break;
            case "decline request" :
                ManagerPanelController.declineRequest(messageInputs.get(0));
                break;
            case "manage categories" :
                ManagerPanelController.manageCategories(messageInputs.get(0), messageInputs.get(1));
                break;
            case "edit category" :

                break;
            case "add category" :

                break;
            case "remove category" :
                break;
            case "view company information" :
                SellerPanelController.companyInfo();
                break;
            case "view sales history" :
                SellerPanelController.salesHistory(messageInputs.get(0), messageInputs.get(1));
                break;
            case "manage products" :
                SellerPanelController.manageProducts();
                break;
            case "view product" :
                SellerPanelController.viewProduct(messageInputs.get(0));
                break;
            case "view product buyers" :
                SellerPanelController.viewBuyers(messageInputs.get(0));
                break;
            case "edit product" :

                break;
            case "add product" :

                break;
            case "remove product seller" :
                SellerPanelController.removeProduct(messageInputs.get(0));
                break;
            case "show categories" :

                break;
            case "view offs" :
                SellerPanelController.showSellerOffs(messageInputs.get(0), messageInputs.get(1));
                break;
            case "view off" :
                SellerPanelController.showOff(messageInputs.get(0));
                break;
            case "edit off" :

                break;
            case "add off" :

                break;
            case "view balance" :

                break;
            case "show products in cart" :
                CustomerPanelController.viewProductsInCart();
                break;
            case "view product in cart" :
                break;
            case "increase product in cart" :
                CustomerPanelController.increaseProductInCart(messageInputs.get(0), messageInputs.get(1));
                break;
            case "decrease product in cart" :
                CustomerPanelController.decreaseProductInCart(messageInputs.get(0), messageInputs.get(1));
                break;
            case "show total price of cart" :
                CustomerPanelController.cartPrice();
                break;
            case "purchase cart" :

                break;
            case "view orders" :
                CustomerPanelController.viewOrders();
                break;
            case "show order" :
                CustomerPanelController.viewOrder(messageInputs.get(0));
                break;
            case "rate product" :
                CustomerPanelController.rate(messageInputs.get(0), Integer.parseInt(messageInputs.get(0)));
                break;
            case "view discount codes customer" :
                CustomerPanelController.viewUserDiscountCode();
                break;
            case "view categories" :

                break;
            case "show available filters" :

                break;
            case "filter an available filter" :
                break;
            case "current filters" :
                break;
            case "disable a selected filter" :
                break;
            case "show available sorts" :
                break;
            case "sort an available sort" :
                break;
            case "show current sort" :
                break;
            case "disable sort" :
                break;
            case "show products" :
                break;
            case "show product" :
                break;
            case "digest" :
                break;
            case "add to cart" :
                break;
            case "select seller" :
                break;
            case "product attributes" :
                break;
            case "compare products" :
                break;
            case "comments" :
                break;
            case "add comment" :
                break;
            case "offs" :
                break;
        }
    }

}
