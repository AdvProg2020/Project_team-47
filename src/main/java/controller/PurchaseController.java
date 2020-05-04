package controller;

import model.discount.DiscountCode;
import model.log.BuyLog;
import model.others.Date;
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
        if (!information.containsKey("address") ||
                !information.containsKey("postal-code") ||
                !information.containsKey("phone-number") ||
                !information.containsKey("other-requests")) {

            sendError("Not enough information!!");
            return;
        }
        purchaseLog.setAddress(information.get("address"));
        purchaseLog.setPhoneNumber(information.get("phone-number"));
        purchaseLog.setPostalCode(information.get("postal-code"));
        purchaseLog.setCustomerRequests(information.get("other-requests"));
    }

    public static void applyDiscountCode(String code) {
        Customer customer = (Customer) loggedUser;
        discountCode = customer.getDiscountCode(code);
        if (discountCode==null) {
            sendError("You don't have this discount code!!");
            return;
        } else if (!discountCode.canThisPersonUseCode(customer)) {
            sendError("Sorry you can't use this code!!");
        }
        sendAnswer(Double.toString(discountCode.getPriceAfterApply(getShoppingCart().getTotalPrice())));
    }

    public static void pay() {
        Customer customer = (Customer) loggedUser;
        ShoppingCart shoppingCart = getShoppingCart();
        shoppingCart.update();
        double cartPrice = shoppingCart.getTotalPrice();
        purchaseLog.setAppliedDiscount(0);
        if (discountCode != null&&discountCode.canThisPersonUseCode(customer)) {
            cartPrice = discountCode.appliedDiscount(cartPrice);
            purchaseLog.setAppliedDiscount(cartPrice);
            discountCode.codeUsed(customer);
        }
        purchaseLog.setLogDate(Date.getCurrentDate());
        purchaseLog.setMoney(cartPrice);
        if (!customer.canUserBuy(cartPrice)) {
            sendError("You don't have enough money to buy this products!!");
            return;
        }
        purchaseLog.setCustomer(customer);
        shoppingCart.addToBuyLog(purchaseLog);
        purchaseLog.createSellLog();
        purchaseLog.increaseSellerMoney();
        customer.addBuyLog(purchaseLog);
        customer.decreaseMoney(cartPrice);
        shoppingCart.buy();
        purchaseLog = new BuyLog();
    }

    private static ShoppingCart getShoppingCart() {
        ShoppingCart shoppingCart = ((Customer) loggedUser).getShoppingCart();
        return shoppingCart;
    }
}
