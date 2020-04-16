package model.user;

import model.log.Log;
import model.discount.DiscountCode;
import model.others.Product;
import model.others.ShoppingCart;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Log> buyLogs;
    private ShoppingCart shoppingCart;
    private ArrayList<DiscountCode> discountCodes;
    private double money;

    public Customer() {
    }

    public boolean userHasDiscountCode(String code){return true;}
    public boolean canUserBuy(double cartPrice){return true;}

    private void addBuyLog(Log buyLog){}
    public void buy(Log buyLog){}

    public boolean doesUserBoughtProduct(Product product){return true;}
    public String getAllDiscountCodeInfo(){return null;}


    public String getAllOrdersInfo(){return null;}



    public boolean doesCustomerOrdered(String productId){return true;}

    public void rate(int score,String productId){}

    @Override
    public String userInfoForSending() {
        return null;
    }
}
