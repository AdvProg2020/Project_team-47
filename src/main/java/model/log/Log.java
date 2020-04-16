package model.log;

import model.others.Date;
import model.others.Product;
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


    public Log() {
    }

    public boolean isThereProductInLog(Product product){return true;}
    public User getCustomer() {
        return customer;
    }

    public ArrayList<Product> getPurchasedWares() {
        return purchasedWares;
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
}
