package model.log;

import model.others.Date;
import model.others.Product;
import model.user.Seller;
import model.user.User;

abstract public class Log {
    protected String logId;
    protected Date logDate;
    protected double appliedDiscount;
    protected User customer;
    protected String purchaseStatus;
    private double money;

    public Log() {
        this.logId = logIdCreator();
    }

    private String logIdCreator() {
        return null;
    }


    abstract public String getLogInfoForSending();

    public boolean isThereProductInLog(Product product) {
        return true;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public boolean isThereProductInLogWithThisSeller(Product product, Seller seller) {
        return true;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

}
