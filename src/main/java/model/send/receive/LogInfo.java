package model.send.receive;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LogInfo {
    private String customer;
    private String logId;
    private Date logDate;
    private double price;
    private String status;
    private String logType;//can be "buy log" or "sell log"
    private String address;//for buy log
    private String postalCode;//for buy log
    private String phoneNumber;//for buy log
    private String customerRequest;//for buy log
    private double appliedDiscount;//for buy log
    private String seller;//for sell log
    private ArrayList<ProductInLog> productInLogs;

    public LogInfo(String logId, Date logDate, String logType) {
        this.logId = logId;
        this.logDate = logDate;
        this.logType = logType;
        this.productInLogs = new ArrayList<>();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addProduct(UserInfo seller, ProductInfo productInfo, int number) {
        ProductInLog productInLog = new ProductInLog();
        productInLog.sellerUsername = seller.getUsername();
        productInLog.number = number;
        productInLog.productNameId.put(productInfo.getName(), productInfo.getId());
        productInLogs.add(productInLog);
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCustomerRequest(String customerRequest) {
        this.customerRequest = customerRequest;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    private static class ProductInLog {
        private String sellerUsername;
        private HashMap<String, String> productNameId;
        private int number;

        private ProductInLog() {
            this.productNameId = new HashMap<>();
        }
    }

    public String getCustomer() {
        return customer;
    }

    public String getLogId() {
        return logId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getLogType() {
        return logType;
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

    public String getCustomerRequest() {
        return customerRequest;
    }

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public String getSeller() {
        return seller;
    }

    public ArrayList<ProductInLog> getProductInLogs() {
        return productInLogs;
    }
}
