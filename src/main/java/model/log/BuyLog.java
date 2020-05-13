package model.log;


import model.others.Product;
import model.send.receive.LogInfo;
import model.send.receive.ProductInfo;
import model.send.receive.UserInfo;
import model.user.Seller;

import java.util.ArrayList;

public class BuyLog extends Log {
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String customerRequests;
    private ArrayList<ProductsInLog> products;


    public BuyLog() {
        super();
        products = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "BuyLog{}";
    }//

    @Override
    public LogInfo getLogInfoForSending() {
        return null;
    }//

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerRequests() {
        return customerRequests;
    }

    public void setCustomerRequests(String customerRequests) {
        this.customerRequests = customerRequests;
    }

    public void addProduct(Seller seller, Product product, int number) {

    }

    public void createSellLog() {

    }


    private class ProductsInLog {
        private ProductInfo product;
        private UserInfo seller;
        private int number;
    }
}
