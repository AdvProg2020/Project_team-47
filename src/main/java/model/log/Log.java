package model.log;

import model.others.Date;
import model.others.Product;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;

abstract public class Log {
    protected static int logIdCounter;
    private double money;
    protected String logId;
    protected Date  logDate;
    protected double appliedDiscount;
    protected User customer;
    protected String address;
    protected String postalCode;
    protected String phoneNumber;
    protected String customerRequests;
    protected String purchaseStatus;
    protected ArrayList<Product> purchasedWares;

    abstract public String getLogInfoForSending();


    public Log(double money, String logId, Date logDate,
               double appliedDiscount, User customer, String address,
               String postalCode, String phoneNumber, String customerRequests,
               String purchaseStatus, ArrayList<Product> purchasedWares) {
        this.money = money;
        this.logId = logId;
        this.logDate = logDate;
        this.appliedDiscount = appliedDiscount;
        this.customer = customer;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.customerRequests = customerRequests;
        this.purchaseStatus = purchaseStatus;
        this.purchasedWares = purchasedWares;
    }

    public boolean isThereProductInLog(Product product){
        return purchasedWares.contains(product);
    }
    public User getCustomer() {
        return customer;
    }

    public ArrayList<Product> getPurchasedWares() {
        return purchasedWares;
    }

    public boolean isThereProductInLogWithThisSeller(Product product, Seller seller) {
        //todo
        return true;
    }


    public void setMoney(double money) {
        this.money = money;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public static int getLogIdCounter() {
        return logIdCounter;
    }

    public double getMoney() {
        return money;
    }

    public String getLogId() {
        return logId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerRequests() {
        return customerRequests;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }
}
