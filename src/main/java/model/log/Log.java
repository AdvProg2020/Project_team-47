package model.log;

import controller.Controller;
import database.Database;
import model.send.receive.LogInfo;
import model.send.receive.UserInfo;

import java.util.Date;
import java.util.TreeSet;

abstract public class Log {
    private static TreeSet<String> usedId;
    protected String logId;
    protected Date logDate;
    protected UserInfo customer;
    protected String purchaseStatus;
    protected double price;

    public Log() {
        this.logId = logIdCreator();
        usedId.add(this.logId);
        Database.updateUsedId(usedId);
    }

    public static void setUsedId(TreeSet<String> usedId) {
        Log.usedId = usedId;
    }

    private String logIdCreator() {
        String id = Controller.idCreator();
        if (usedId.contains(id))
            return logIdCreator();
        return id;
    }

    public abstract void updateDatabase();

    public abstract LogInfo getLogInfoForSending();

    public abstract boolean isThereProductInLog(String productId);

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public UserInfo getCustomer() {
        return customer;
    }

    public void setCustomer(UserInfo customer) {
        this.customer = customer;
    }

}
