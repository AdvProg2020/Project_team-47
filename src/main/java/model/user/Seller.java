package model.user;

import model.log.Log;
import model.discount.Off;
import model.others.Product;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private String companyInfo;
    private ArrayList<Log> sellLogs;
    private ArrayList<Product> allProducts;
    private ArrayList<Off> allOff;
    private double money;
    private String address;


    public Seller() {
    }

    public static String getAllSellLogsInfo(String sortField, String sortDirection){return null;}


    public static String getAllProductInfo(String sortField,String sortDirection){return null;}


    public static String getAllOffsInfo(String sortField,String sortDirection){return null;}

    public boolean sellerHasThisOff(String offId){return true;}
    public String getOff(String offId){return null;}

    public void editOff(String offId,String field,Object newValue){}


    public static String getBuyers(String productId){return null;}


    private void addSellLog(Log sellLog){}


    public void buy(Log sellLog){}

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public ArrayList<Log> getSellLogs() {
        return sellLogs;
    }

    @Override
    public String userInfoForSending() {
        return null;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Off> getAllOff() {
        return allOff;
    }

    public double getMoney() {
        return money;
    }

    public String getAddress() {
        return address;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public void setSellLogs(ArrayList<Log> sellLogs) {
        this.sellLogs = sellLogs;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setAllOff(ArrayList<Off> allOff) {
        this.allOff = allOff;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
