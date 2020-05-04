package model.log;


import model.others.Product;
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
    public String getLogInfoForSending() {
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

    public void increaseSellerMoney() {

    }


    private class ProductsInLog {
        private Product product;
        private Seller seller;
        private int number;
    }
}
