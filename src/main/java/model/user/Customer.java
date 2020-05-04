package model.user;

import com.google.gson.Gson;
import model.discount.DiscountCode;
import model.log.BuyLog;
import model.log.Log;
import model.others.Product;
import model.others.Score;
import model.others.ShoppingCart;
import model.send.receive.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private ArrayList<BuyLog> buyLogs;
    private ShoppingCart shoppingCart;
    private ArrayList<DiscountCode> discountCodes;
    private double money;

    public Customer() {
        super();
        buyLogs=new ArrayList<>();
        discountCodes = new ArrayList<>();
    }

    public Customer(HashMap<String, String> userInfo) {
        super(userInfo);
    }

    @Override
    public void deleteUser() {
        allUsers.remove(this);
        for (DiscountCode discountCode : this.discountCodes) {
            discountCode.getUsersAbleToUse().remove(this);
        }
    }

    public DiscountCode getDiscountCode(String code) {
        for (DiscountCode discountCode : discountCodes) {
            if (discountCode.getDiscountCode().equals(code)) {
                return discountCode;
            }
        }
        return null;
    }

    public void decreaseMoney(double money) {
        this.money -= money;
    }

    public BuyLog getBuyLog(String id) {
        for (BuyLog buyLog : buyLogs) {
            if (buyLog.getLogId().equals(id)) {
                return buyLog;
            }
        }
        return null;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
    }

    public boolean customerHasDiscountCode(String code) {
        for (DiscountCode discountCode : this.discountCodes) {
            if (code.equals(discountCode.getDiscountCode())) {
                return true;
            }
        }
        return false;
    }

    public boolean canUserBuy(double cartPrice) {
        return !(this.money < cartPrice);
    }

    public void addBuyLog(BuyLog buyLog) {
        buyLogs.add(buyLog);
    }

    public boolean doesUserBoughtProduct(Product product) {
        for (Log buyLog : buyLogs) {
            if (buyLog.isThereProductInLog(product)) {
                return true;
            }
        }
        return false;
    }

    public String getAllDiscountCodeInfo() {
        ArrayList<String> discountCodesInfo = new ArrayList<>();
        for (DiscountCode discountCode : this.discountCodes) {
            discountCodesInfo.add(discountCode.discountInfoForSending());
        }
        Gson discountCodesGson = new Gson();
        return discountCodesGson.toJson(discountCodesInfo);
    }


    public String getAllOrdersInfo() {
        ArrayList<String> ordersInfo = new ArrayList<>();
        for (Log buyLog : buyLogs) {
            ordersInfo.add(buyLog.getLogInfoForSending());
        }
        Gson ordersInfoGson = new Gson();
        return ordersInfoGson.toJson(ordersInfo);
    }


    public boolean doesCustomerOrdered(String productId) {
        Product product = Product.getProductWithId(productId);
        return doesUserBoughtProduct(product);
    }


    public void rate(int score, Product product) {
        Score newScore = new Score();
        newScore.setProduct(product);
        newScore.setScore(score);
        newScore.setWhoSubmitScore(this);
        product.addScore(newScore);
    }

    @Override
    public String userInfoForSending() {
        UserInfo user = new UserInfo();
        user.setEmail(this.getEmail());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setPhoneNumber(this.getPhoneNumber());
        user.setUsername(this.getUsername());
        user.setMoney(this.money);
        return (new Gson()).toJson(user);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void addDiscountCode(DiscountCode discountCode) {
        if (!this.discountCodes.contains(discountCode)) {
            this.discountCodes.add(discountCode);
        }
    }

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public double getMoney() {
        return money;
    }
}
