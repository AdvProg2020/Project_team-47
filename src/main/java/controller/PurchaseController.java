package controller;

import model.discount.DiscountCode;
import model.log.BuyLog;
import model.others.ShoppingCart;
import model.user.Customer;

import java.util.HashMap;

public class PurchaseController extends Controller {
    private static BuyLog purchaseLog;
    private static DiscountCode discountCode;

    static {
        purchaseLog = new BuyLog();
    }

    public static void purchase() {
        //this function will update cart and send cart info

        if (loggedUser == null) {
            sendError("You should log in first!!");
            return;
        }
        purchaseLog = new BuyLog();
        discountCode = null;
        getShoppingCart().update();
        sendAnswer(getShoppingCart().cartInfo());
    }

    public static void gettingPurchaseInformation(HashMap<String, String> information) {
        //this function will get purchase information

        if (!checkingPurchaseInfo(information)) {
            sendError("Not enough information!!");
            return;
        }
        purchaseLog.setAddress(information.get("address"));
        purchaseLog.setPhoneNumber(information.get("phone-number"));
        purchaseLog.setPostalCode(information.get("postal-code"));
        purchaseLog.setCustomerRequests(information.get("other-requests"));
        actionCompleted();
    }

    private static boolean checkingPurchaseInfo(HashMap<String, String> information) {
        //check that purchase information HashMap has all key that should has

        String[] infoKey = {"address", "postal-code", "phone-number", "other-requests"};
        for (String key : infoKey) {
            if (!information.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public static void applyDiscountCode(String code) {
        //apply discount to this purchase

        Customer customer = (Customer) loggedUser;
        discountCode = customer.getDiscountCode(code);

        //checking errors that may happen during apply code
        if (discountCode == null) {
            sendError("You don't have this discount code!!");
            return;
        } else if (!discountCode.canThisPersonUseCode(customer)) {
            sendError("Sorry you can't use this code!!");
            discountCode = null;
            return;
        }

        //sending cart price after applying code successfully
        sendAnswer(discountCode.getPriceAfterApply(getShoppingCart().getTotalPrice()));
    }

    public static void pay() {
        //this function is the last step for buying

        Customer customer = (Customer) loggedUser;
        ShoppingCart shoppingCart = getShoppingCart();
        shoppingCart.update();

        double finalPrice = getFinalPrice(shoppingCart.getTotalPrice());
        //check if customer has enough money
        if (!customer.canUserBuy(finalPrice)) {
            sendError("You don't have enough money to buy this products!!");
            return;
        }

        useDiscountCode(shoppingCart);

        shoppingCart.buy();
        purchaseLog.setPrice(finalPrice);
        customer.decreaseMoney(finalPrice);
        setPurchaseLogInfo(shoppingCart);

        customer.sendBuyingEmail(purchaseLog.getLogId());

        finishingPurchasing();
        actionCompleted();
    }

    private static void finishingPurchasing() {
        purchaseLog = new BuyLog();
    }

    private static void setPurchaseLogInfo(ShoppingCart shoppingCart) {
        //this function will set some information on buying log such as date ...
        Customer customer = (Customer) loggedUser;
        purchaseLog.setLogDate(Controller.getCurrentTime());
        purchaseLog.setCustomer(customer.userInfoForSending());
        shoppingCart.addToBuyLog(purchaseLog);//add shopping cart item to log
        customer.addBuyLog(purchaseLog);
        customer.updateDatabase().update();
        purchaseLog.createSellLog();//should create sell log for sellers
    }

    private static double getFinalPrice(double cartPrice) {
        //this function will get cart price and return final value after applying discount(if exist)

        Customer customer = (Customer) loggedUser;
        if (discountCode != null && discountCode.canThisPersonUseCode(customer)) {
            cartPrice = discountCode.getPriceAfterApply(cartPrice);
        }
        return cartPrice;
    }

    private static void useDiscountCode(ShoppingCart shoppingCart) {
        //this function will use discount and if there was no code it will set purchase log info

        Customer customer = (Customer) loggedUser;
        double cartPrice = shoppingCart.getTotalPrice();
        purchaseLog.setAppliedDiscount(0);

        if (discountCode != null && discountCode.canThisPersonUseCode(customer)) {
            purchaseLog.setAppliedDiscount(discountCode.appliedDiscount(cartPrice));
            discountCode.codeUsed(customer);
        }
    }

    private static ShoppingCart getShoppingCart() {
        //this function will return customer shopping cart who logged in

        ShoppingCart shoppingCart = ((Customer) loggedUser).getShoppingCart();
        return shoppingCart;
    }
}//end purchase controller class
