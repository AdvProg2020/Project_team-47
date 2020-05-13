package model.log;

import model.others.Date;
import model.others.Product;
import model.send.receive.LogInfo;
import model.send.receive.UserInfo;
import model.user.Seller;
import model.user.User;

abstract public class Log {
    protected String logId;
    protected Date logDate;
    protected double appliedDiscount;
    protected UserInfo customer;
    protected String purchaseStatus;
    private double money;

    public Log() {
        this.logId = logIdCreator();
    }

    private String logIdCreator() {
        return null;
    }


    public abstract LogInfo getLogInfoForSending();

    public boolean isThereProductInLog(Product product) {
        return true;
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

    public void setCustomer(UserInfo customer) {
        this.customer = customer;
    }

    public UserInfo getCustomer() {
        return customer;
    }
}
