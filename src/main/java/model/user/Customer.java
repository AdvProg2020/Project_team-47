package model.user;

import bank.StoreToBankConnection;
import database.UserData;
import graphic.Client;
import model.discount.DiscountCode;
import model.ecxeption.purchase.CodeException;
import model.log.BuyLog;
import model.log.Log;
import model.others.Product;
import model.others.Score;
import model.others.ShoppingCart;
import model.send.receive.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private final ArrayList<BuyLog> buyLogs;
    private final ArrayList<DiscountCode> discountCodes;
    private ShoppingCart shoppingCart;
    private double money;


    public Customer() {
        super();
        buyLogs = new ArrayList<>();
        discountCodes = new ArrayList<>();
        shoppingCart = new ShoppingCart();
        this.money = 10000000;
    }

    public Customer(HashMap<String, String> userInfo, byte[] avatar) {
        super(userInfo, avatar);
        shoppingCart = new ShoppingCart();
        buyLogs = new ArrayList<>();
        discountCodes = new ArrayList<>();
        this.money = 0;
        try {
            StoreToBankConnection.getInstance().createAccount(this.getFirstName()
                    , this.getLastName(), this.getUsername()
                    , this.getPassword(), this.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser() {
        allUsers.remove(this);
        for (DiscountCode discountCode : this.discountCodes) {
            discountCode.removeCustomer(this);
        }
        this.removeFromDatabase();
    }

    public void purchaseCompleted() {
        this.shoppingCart = new ShoppingCart();
    }



    public DiscountCode getDiscountCode(String code) throws CodeException.DontHaveCode {
        for (DiscountCode discountCode : discountCodes) {
            if (discountCode.getDiscountCode().equals(code)) {
                return discountCode;
            }
        }
        throw new CodeException.DontHaveCode();
    }

    public void decreaseMoney(double money) {
        this.money -= money;
        this.updateDatabase().update();
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
        //this function will call when a discount code should remove to delete
        //intended code from users who has that code
        this.discountCodes.remove(discountCode);
        this.updateDatabase().update();
    }


    public boolean canUserBuy(double cartPrice, String source) throws Exception {
        if (source.equals("wallet")) {
            return this.getAllowedMoney() >= cartPrice;
        }
        if (source.equals("bank")) {
            ServerMessage answer = null;
            try {
                answer = StoreToBankConnection.getInstance().getToken(this.getUsername(), getPassword());
                if (answer.getType().equals("Error")) {
                    System.out.println("error in getting token");
                }
                answer = StoreToBankConnection.getInstance().getBalance("" + answer.getToken().getId());
                if (answer.getType().equals("Error")) {
                    System.out.println("error in getting balance");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Integer.parseInt(answer.getFirstString()) >= cartPrice;
        } else {
            throw new Exception("invalid type of source");
        }
    }

    public void addBuyLog(BuyLog buyLog) {
        buyLogs.add(buyLog);
    }

    public boolean doesUserBoughtProduct(Product product) {
        for (Log buyLog : buyLogs) {
            if (buyLog.isThereProductInLog(product.getId())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<DiscountCodeInfo> getAllDiscountCodeInfo() {
        ArrayList<DiscountCodeInfo> discountCodesInfo = new ArrayList<>();
        for (DiscountCode discountCode : this.discountCodes) {
            discountCodesInfo.add(discountCode.discountCodeInfo());
        }
        return discountCodesInfo;
    }

    public ArrayList<LogInfo> getAllOrdersInfo() {
        ArrayList<LogInfo> ordersInfo = new ArrayList<>();
        for (Log buyLog : buyLogs) {
            ordersInfo.add(buyLog.getLogInfoForSending());
        }
        return ordersInfo;
    }

    public void rate(int score, Product product) {
        Score newScore = new Score();
        newScore.setProduct(product.getId());
        newScore.setScore(score);
        newScore.setWhoSubmitScore(this.getUsername());
        product.addScore(newScore);
        product.updateDatabase();
    }

    @Override
    public UserInfo userInfoForSending() {
        UserInfo user = new UserInfo();
        userInfoSetter(user);
        user.setMoney(this.money);
        user.setType("customer");
        return user;
    }

    @Override
    public UserData updateDatabase() {
        UserData user = new UserData("customer");
        super.updateDatabase(user);
        user.setMoney(this.money);
        addLogToDatabase(user);
        addDiscountCodeToDatabase(user);
        return user;
    }

    private void addLogToDatabase(UserData user) {
        for (BuyLog buyLog : buyLogs) {
            user.addLog(buyLog.getLogId());
        }
    }

    private void addDiscountCodeToDatabase(UserData user) {
        for (DiscountCode discountCode : discountCodes) {
            user.addDiscountCode(discountCode.getDiscountCode());
        }
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void addDiscountCode(DiscountCode discountCode) {
        if (!this.discountCodes.contains(discountCode)) {
            this.discountCodes.add(discountCode);
        }
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
