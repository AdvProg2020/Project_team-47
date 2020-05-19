package controller.purchase;

import controller.Command;
import controller.Controller;
import controller.Error;
import model.discount.DiscountCode;
import model.log.BuyLog;
import model.others.Email;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.user.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static controller.Controller.*;

public abstract class PurchaseCommands extends Command {
    public static PurchaseInitializerCommand getPurchaseInitializerCommand() {
        return PurchaseInitializerCommand.getInstance();
    }

    public static PurchaseInformationGetter getPurchaseInformationGetter() {
        return PurchaseInformationGetter.getInstance();
    }

    public static ApplyDiscountCodeCommand getApplyDiscountCodeCommand() {
        return ApplyDiscountCodeCommand.getInstance();
    }

    public static PayCommand getPayCommand() {
        return PayCommand.getInstance();
    }

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Customer)) {
            sendError(Error.NEED_CUSTOMER.getError());
            return false;
        }
        return true;
    }

    protected ShoppingCart getShoppingCart() {
        //this function will return customer shopping cart who logged in

        ShoppingCart shoppingCart = ((Customer) getLoggedUser()).getShoppingCart();
        return shoppingCart;
    }

    protected void resetPurchasePage() {
        PurchaseController.getInstance().resetPurchasePage();
    }

    protected BuyLog purchaseLog() {
        return PurchaseController.getInstance().purchaseLog();
    }

    protected DiscountCode purchaseCode() {
        return PurchaseController.getInstance().purchaseCode();
    }

    protected void setCode(DiscountCode code) {
        PurchaseController.getInstance().setCode(code);
    }
}


class PurchaseInitializerCommand extends PurchaseCommands {
    private static PurchaseInitializerCommand command;

    private PurchaseInitializerCommand() {
        this.name = "purchase cart";
    }

    public static PurchaseInitializerCommand getInstance() {
        if (command != null)
            return command;
        command = new PurchaseInitializerCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        purchase();
    }

    private void purchase() {
        //this function will update cart and send cart info

        if (getLoggedUser() == null) {
            sendError("You should log in first!!");
            return;
        }
        resetPurchasePage();
        getShoppingCart().update();
        sendAnswer(getShoppingCart().cartInfo());
    }
}


class PurchaseInformationGetter extends PurchaseCommands {
    private static PurchaseInformationGetter command;

    private PurchaseInformationGetter() {
        this.name = "get receiver information";
    }

    public static PurchaseInformationGetter getInstance() {
        if (command != null)
            return command;
        command = new PurchaseInformationGetter();
        return command;
    }


    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstHashMap()))
            return;
        gettingPurchaseInformation(request.getFirstHashMap());
    }

    private void gettingPurchaseInformation(HashMap<String, String> information) {
        //this function will get purchase information

        if (!checkingPurchaseInfo(information)) {
            sendError("Not enough information!!");
            return;
        }
        purchaseLog().setAddress(information.get("address"));
        purchaseLog().setPhoneNumber(information.get("phone-number"));
        purchaseLog().setPostalCode(information.get("postal-code"));
        purchaseLog().setCustomerRequests(information.get("other-requests"));
        actionCompleted();
    }

    private boolean checkingPurchaseInfo(HashMap<String, String> information) {
        //check that purchase information HashMap has all key that should has

        String[] infoKey = {"address", "postal-code", "phone-number", "other-requests"};
        for (String key : infoKey) {
            if (!information.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
}


class ApplyDiscountCodeCommand extends PurchaseCommands {
    private static ApplyDiscountCodeCommand command;

    private ApplyDiscountCodeCommand() {
        this.name = "using discount code";
    }

    public static ApplyDiscountCodeCommand getInstance() {
        if (command != null)
            return command;
        command = new ApplyDiscountCodeCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstString()))
            return;
        applyDiscountCode(request.getFirstString());
    }


    private void applyDiscountCode(String code) {
        //apply discount to this purchase

        Customer customer = (Customer) getLoggedUser();
        setCode(customer.getDiscountCode(code));

        //checking errors that may happen during apply code
        if (purchaseCode() == null) {
            sendError("You don't have this discount code!!");
            return;
        } else if (!purchaseCode().canThisPersonUseCode(customer)) {
            sendError("Sorry you can't use this code!!");
            setCode(null);
            return;
        }

        //sending cart price after applying code successfully
        sendAnswer(purchaseCode().getPriceAfterApply(getShoppingCart().getTotalPrice()));
    }

}

class PayCommand extends PurchaseCommands {
    private static PayCommand command;

    private PayCommand() {
        this.name = "pay";
    }

    public static PayCommand getInstance() {
        if (command != null)
            return command;
        command = new PayCommand();
        return command;
    }

    @Override
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        pay();
    }

    private void pay() {
        //this function is the last step for buying

        Customer customer = (Customer) getLoggedUser();
        ShoppingCart shoppingCart = getShoppingCart();
        shoppingCart.update();

        double finalPrice = getFinalPrice(shoppingCart.getTotalPrice());
        //check if customer has enough money
        if (!customer.canUserBuy(finalPrice)) {
            sendError("You don't have enough money to buy this products!!");
            return;
        } else if (finalPrice > 1000000) {
            giveGift(customer, finalPrice);
        }

        useDiscountCode(shoppingCart);

        shoppingCart.buy();
        purchaseLog().setPrice(finalPrice);
        customer.decreaseMoney(finalPrice);
        setPurchaseLogInfo(shoppingCart);

        customer.sendBuyingEmail(purchaseLog().getLogId());

        finishingPurchasing();
        actionCompleted();
    }

    private void useDiscountCode(ShoppingCart shoppingCart) {
        //this function will use discount and if there was no code it will set purchase log info

        Customer customer = (Customer) getLoggedUser();
        double cartPrice = shoppingCart.getTotalPrice();
        purchaseLog().setAppliedDiscount(0);

        if (purchaseCode() != null && purchaseCode().canThisPersonUseCode(customer)) {
            purchaseLog().setAppliedDiscount(purchaseCode().appliedDiscount(cartPrice));
            purchaseCode().codeUsed(customer);
        }
    }

    private void finishingPurchasing() {
        resetPurchasePage();
    }

    private void setPurchaseLogInfo(ShoppingCart shoppingCart) {
        //this function will set some information on buying log such as date ...
        Customer customer = (Customer) getLoggedUser();
        purchaseLog().setLogDate(Controller.getCurrentTime());
        purchaseLog().setCustomer(customer.userInfoForSending());
        shoppingCart.addToBuyLog(purchaseLog());//add shopping cart item to log
        customer.addBuyLog(purchaseLog());
        customer.updateDatabase().update();
        purchaseLog().createSellLog();//should create sell log for sellers
    }

    double getFinalPrice(double cartPrice) {
        //this function will get cart price and return final value after applying discount(if exist)

        Customer customer = (Customer) getLoggedUser();
        if (purchaseCode() != null && purchaseCode().canThisPersonUseCode(customer)) {
            cartPrice = purchaseCode().getPriceAfterApply(cartPrice);
        }
        return cartPrice;
    }

    private void giveGift(Customer customer, double shoppingPrice) {
        DiscountCode discountCode = new DiscountCode((int) (shoppingPrice / 50), 1);
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer);
        discountCode.setUsersAbleToUse(customerArrayList);
        discountCode.setStartTime(getCurrentTime());
        discountCode.setFinishTime(new Date(getCurrentTime().getTime() + 604800000));
        discountCode.setPercent(20);
        customer.addDiscountCode(discountCode);
        discountCode.updateDatabase();
        customer.updateDatabase().update();

        Email newEmail = new Email(customer.getEmail());
        newEmail.setMessage(discountCode.getDiscountCode());
        newEmail.sendDiscountEmail(20);
    }


}