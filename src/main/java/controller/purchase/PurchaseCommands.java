package controller.purchase;

import bank.StoreToBankConnection;
import controller.Command;
import controller.Controller;
import model.discount.DiscountCode;
import model.ecxeption.Exception;
import model.ecxeption.common.NotEnoughInformation;
import model.ecxeption.purchase.CodeException;
import model.ecxeption.purchase.NotEnoughMoneyException;
import model.ecxeption.user.UserTypeException;
import model.log.BuyLog;
import model.others.Email;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;

import java.io.IOException;
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

    protected ShoppingCart getShoppingCart() {
        //this function will return customer shopping cart who logged in
        return ((Customer) getLoggedUser()).getShoppingCart();
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
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException {
        return purchase();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage purchase() {
        //this function will update cart and send cart info
        resetPurchasePage();
        getShoppingCart().update();
        return sendAnswer(getShoppingCart().cartInfo());
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
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap());
        checkPrimaryErrors(request);
        gettingPurchaseInformation(request.getHashMap());
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
        if (!checkingPurchaseInfo(request.getHashMap()))
            throw new NotEnoughInformation();
    }

    private void gettingPurchaseInformation(HashMap<String, String> information) {
        //this function will get purchase information
        purchaseLog().setAddress(information.get("address"));
        purchaseLog().setPhoneNumber(information.get("phone-number"));
        purchaseLog().setPostalCode(information.get("postal-code"));
        purchaseLog().setCustomerRequests(information.get("other-requests"));
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
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap(), request.getHashMap().get("code"));
        return applyDiscountCode(request.getHashMap().get("code"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {
    }


    private ServerMessage applyDiscountCode(String code) throws CodeException.DontHaveCode, CodeException.CantUseCodeException {
        //apply discount to this purchase

        Customer customer = (Customer) getLoggedUser();
        DiscountCode discountCode = customer.getDiscountCode(code);
        //checking errors that may happen during apply code
        if (!discountCode.canThisPersonUseCode(customer))
            throw new CodeException.CantUseCodeException();
        setCode(discountCode);

        //sending cart price after applying code successfully
        return sendAnswer(purchaseCode().getPriceAfterApply(getShoppingCart().getTotalPrice()));
    }

}

class PayCommand extends PurchaseCommands {
    private static PayCommand command;

    private PayCommand() {
        this.name = "pay with (bank|wallet)";
    }

    public static PayCommand getInstance() {
        if (command != null)
            return command;
        command = new PayCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException, NotEnoughMoneyException {
        System.out.println("salam");
        pay(request.getType().split("\\s")[2]);
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void pay(String source) throws NotEnoughMoneyException {
        //this function is the last step for buying
        //source can be wallet or bank
        Customer customer = (Customer) getLoggedUser();
        ShoppingCart shoppingCart = getShoppingCart();
        shoppingCart.update();

        double finalPrice = getFinalPrice(shoppingCart.getTotalPrice());
        //check if customer has enough money
        try {
            if (!customer.canUserBuy(finalPrice, source)) {
                throw new NotEnoughMoneyException();
            } else if (finalPrice > 1000000) {
                giveGift(customer, finalPrice);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        useDiscountCode(shoppingCart);

        shoppingCart.buy();
        purchaseLog().setPrice(finalPrice);
        decreaseMoneyFromCustomer(source, finalPrice, customer);

        setPurchaseLogInfo(shoppingCart);

        customer.sendBuyingEmail(purchaseLog().getLogId());

        customer.purchaseCompleted();
        finishingPurchasing();
    }

    private void decreaseMoneyFromCustomer(String source, double finalPrice, Customer customer) {
        if (source.equals("wallet")) {
            customer.decreaseMoney(finalPrice);
        } else if (source.equals("bank")){
            payWithBankAccount(finalPrice, customer);
        }
    }

    private void payWithBankAccount(double finalPrice, Customer customer) {
        ServerMessage answer;
        try {
            answer = StoreToBankConnection.getInstance().getToken(customer.getUsername(), customer.getPassword());
            if (answer.getType().equals("Error")) {
                System.out.println("error in getting token");
            }
            answer = StoreToBankConnection.getInstance().createReceipt("" + answer.getToken().getId()
                    , "withdraw", "" + (int)finalPrice, customer.getUsername(), customer.getUsername());
            if (answer.getType().equals("Error")) {
                System.out.println("error in creating receipt");
            }
            answer = StoreToBankConnection.getInstance().pay("" + answer.getReceipt().getReceiptId());
            if (answer.getType().equals("Error")) {
                System.out.println("error in paying receipt");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
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