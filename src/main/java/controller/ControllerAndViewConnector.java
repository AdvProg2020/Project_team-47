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
        ClientMessage message = (new Gson()).fromJson(clientMessage, ClientMessage.class);
        managerCommandProcess(message);
    }

    public static void managerCommandProcess(ClientMessage message) {
        String messageContext = message.getMessageContext();
        ArrayList<String> messageArrayListInputs = message.getMessageArrayListInputs();
        HashMap<String, String> messageHashMapInputs = message.getMessageHashMapInputs();
        switch(messageContext) {
            case "manage users" :
                ManagerPanelController.manageUsers(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "view user" :
                ManagerPanelController.viewUser(messageArrayListInputs.get(0));
                break;
            case "delete user" :
                ManagerPanelController.deleteUser(messageArrayListInputs.get(0));
                break;
            case "create manager profile" :
                ManagerPanelController.createManager(messageHashMapInputs);
                break;
            case "manage all products" :
                ManagerPanelController.
                        manageAllProducts(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "remove product manager" :
                ManagerPanelController.removeProduct(messageArrayListInputs.get(0));
                break;
            case "create discount code" :
                ManagerPanelController.createDiscountCode(messageHashMapInputs, messageArrayListInputs);
                break;
            case "view discount codes manager" :
                ManagerPanelController.
                        viewAllDiscountCodes(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "view discount code manager" :
                ManagerPanelController.viewDiscountCode(messageArrayListInputs.get(0));
                break;
            case "edit discount code manager" :
                ManagerPanelController.editDiscountCode(messageArrayListInputs.get(0),
                        messageArrayListInputs.get(1), messageArrayListInputs.get(2));
                break;
            case "remove discount code manager" :
                ManagerPanelController.removeDiscountCode(messageArrayListInputs.get(0));
                break;
            case "manage requests" :
                ManagerPanelController.manageRequest(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "request details" :
                ManagerPanelController.requestDetail(messageArrayListInputs.get(0));
                break;
            case "accept request" :
                ManagerPanelController.acceptRequest(messageArrayListInputs.get(0));
                break;
            case "decline request" :
                ManagerPanelController.declineRequest(messageArrayListInputs.get(0));
                break;
            case "manage categories" :
                ManagerPanelController.manageCategories(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "edit main category" :
                ManagerPanelController.editMainCategory(messageArrayListInputs.get(0),
                        messageArrayListInputs.get(1), messageArrayListInputs.get(2));
                break;
            case "add main category" :
                ManagerPanelController.addCategory(message.getFirstString(), messageArrayListInputs);
                break;
            case "remove main category" :
                ManagerPanelController.removeMainCategory(messageArrayListInputs.get(0));
                break;
            case "edit sub category" :
                ManagerPanelController.editSubCategory(messageArrayListInputs.get(0),
                        messageArrayListInputs.get(1), messageArrayListInputs.get(2));
                break;
            case "add sub category" :
                ManagerPanelController.addSubCategory(message.getFirstString(),
                        message.getSecondString(), messageArrayListInputs);
                break;
            case "remove sub category" :
                ManagerPanelController.removeSubCategory(message.getSecondString());
                break;
            default:
                sellerCommandProcess(message);
        }
    }
    public static void sellerCommandProcess(ClientMessage message) {
        String messageContext = message.getMessageContext();
        ArrayList<String> messageArrayListInputs = message.getMessageArrayListInputs();
        HashMap<String, String> messageHashMapInputs = message.getMessageHashMapInputs();
        switch(messageContext) {
            case "view company information" :
                SellerPanelController.companyInfo();
                break;
            case "view sales history" :
                SellerPanelController.salesHistory(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "manage products seller" :
                SellerPanelController.manageProduct();
                break;
            case "view product" :
                SellerPanelController.viewProduct(messageArrayListInputs.get(0));
                break;
            case "view product buyers" :
                SellerPanelController.viewBuyers(messageArrayListInputs.get(0));
                break;
            case "edit product" :
                //todo
                //SellerPanelController.editProduct();
                break;
            case "add product" :
                SellerPanelController.addProduct(message.getProductInfo(), message.getProductSpecialProperties());
                break;
            case "remove product seller" :
                SellerPanelController.removeProduct(messageArrayListInputs.get(0));
                break;
            case "view offs" :
                SellerPanelController.showSellerOffs(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "view off" :
                SellerPanelController.showOff(messageArrayListInputs.get(0));
                break;
            case "edit off" :
                //todo
                //SellerPanelController.editOff();
                break;
            case "add off" :
                SellerPanelController.addOff(messageHashMapInputs, messageArrayListInputs);
                break;
            case "view balance seller" :
                SellerPanelController.viewBalance();
                break;
            default:
                customerCommandProcess(message);

        }
    }
    public static void customerCommandProcess(ClientMessage message) {
        String messageContext = message.getMessageContext();
        ArrayList<String> messageArrayListInputs = message.getMessageArrayListInputs();
        HashMap<String, String> messageHashMapInputs = message.getMessageHashMapInputs();
        switch(messageContext) {
            case "show products in cart" :
                CustomerPanelController.viewProductsInCart();
                break;
            case "view product in cart" :
                ProductController.showProduct(messageArrayListInputs.get(0));
                break;
            case "increase product in cart" :
                CustomerPanelController.increaseProductInCart(messageArrayListInputs.get(0),
                        messageArrayListInputs.get(1));
                break;
            case "decrease product in cart" :
                CustomerPanelController.decreaseProductInCart(messageArrayListInputs.get(0),
                        messageArrayListInputs.get(1));
                break;
            case "show total price of cart" :
                CustomerPanelController.cartPrice();
                break;
            case "purchase cart" :
                PurchaseController.purchase();
                break;
            case "view orders" :
                CustomerPanelController.viewOrders();
                break;
            case "show order" :
                CustomerPanelController.viewOrder(messageArrayListInputs.get(0));
                break;
            case "rate product" :
                CustomerPanelController.rate(messageArrayListInputs.get(0),
                        Integer.parseInt(messageArrayListInputs.get(0)));
                break;
            case "view discount codes customer" :
                CustomerPanelController.viewUserDiscountCode();
                break;
            case "view balance customer" :
                CustomerPanelController.viewBalance();
                break;
            default:
                generalCommandProcess(message);
        }
    }
    public static void generalCommandProcess(ClientMessage message) {
        String messageContext = message.getMessageContext();
        ArrayList<String> messageArrayListInputs = message.getMessageArrayListInputs();
        HashMap<String, String> messageHashMapInputs = message.getMessageHashMapInputs();
        switch(messageContext) {
            case "register" :
                LoginController.register(messageHashMapInputs);
                break;
            case "login" :
                LoginController.login(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "logout" :
                LoginController.logout();
                break;
            case "view personal info" :
                UserPanelController.personalInfo();
                break;
            case "edit personal info" :
                UserPanelController.edit(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "view categories" :
                AllProductController.viewCategories(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "show available filters products" :
                AllProductController.showAvailableFilter();
                break;
            case "filter an available filter products" :
                AllProductController.filterBy(messageArrayListInputs.get(0), messageArrayListInputs.get(1),
                        messageArrayListInputs.get(2), messageArrayListInputs.get(3));
                break;
            case "current filters products" :
                AllProductController.currentFilters();
                break;
            case "disable a selected filter products" :
                AllProductController.disableFilter(messageArrayListInputs.get(0));
                break;
            case "show available sorts products" :
                AllProductController.showAvailableSorts();
                break;
            case "sort an available sort products" :
                AllProductController.sort(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "show current sort products" :
                AllProductController.currentSort();
                break;
            case "disable sort products" :
                AllProductController.disableSort();
                break;
            case "show products products" :
                AllProductController.showProductsWithFilterAndSort();
                break;
            case "show product" :
                ProductController.showProduct(messageArrayListInputs.get(0));
                break;
            case "digest" :
                ProductController.digest();
                break;
            case "add to cart" :
                ProductController.addToCart(messageArrayListInputs.get(0));
                break;
            /*case "select seller" :
                break;*/
            case "product attributes" :
                ProductController.attributes();
                break;
            case "compare products" :
                ProductController.compare(messageArrayListInputs.get(0));
                break;
            case "comments" :
                ProductController.comment();
                break;
            case "add comment" :
                ProductController.addComment(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "offs" :
                OffController.offs();
                break;
            case "show available filters offs" :
                OffController.showAvailableFilters();
                break;
            case "filter an available filter offs" :
                OffController.voidShowOffs();
                break;
            case "current filters offs" :
                OffController.currentFilter();
                break;
            case "disable a selected filter offs" :
                OffController.disableFilter(messageArrayListInputs.get(0));
                break;
            case "show available sorts offs" :
                OffController.showAvailableSorts();
                break;
            case "sort an available sort offs" :
                OffController.sort(messageArrayListInputs.get(0), messageArrayListInputs.get(1));
                break;
            case "show current sort offs" :
                OffController.currentSort();
                break;
            case "disable sort offs" :
                OffController.disableSort();
                break;
            default:
                systemCommandProcessor(message);
        }
    }

    private static void systemCommandProcessor(ClientMessage message) {
        String messageContext = message.getMessageContext();
        ArrayList<String> messageArrayListInputs = message.getMessageArrayListInputs();
        switch(messageContext) {
            case "is manager signed in" :

                break;
            case "is username taken" :

                break;
        }
    }

}
