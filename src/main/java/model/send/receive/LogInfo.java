package model.send.receive;

import java.util.ArrayList;
import java.util.Date;

public class LogInfo {
    private final String logId;
    private final Date logDate;
    private final String logType;//can be "buy log" or "sell log"
    private final ArrayList<ProductInLog> productInLogs;
    private String customer;
    private double price;
    private String status;
    private String address;//for buy log
    private String postalCode;//for buy log
    private String phoneNumber;//for buy log
    private String customerRequest;//for buy log
    private double appliedDiscount;//for buy log
    private String seller;//for sell log

    public LogInfo(String logId, Date logDate, String logType) {
        this.logId = logId;
        this.logDate = logDate;
        this.logType = logType;
        this.productInLogs = new ArrayList<>();
    }

    public void addProduct(UserInfo seller, ProductInfo productInfo, int number) {
        ProductInLog productInLog = new ProductInLog();
        productInLog.sellerUsername = seller.getUsername();
        productInLog.number = number;
        productInLog.productId = productInfo.getId();
        productInLog.productName = productInfo.getName();
        productInLogs.add(productInLog);
    }

    public ArrayList<String> getProductsInLogForShow() {
        ArrayList<String> productsInfo = new ArrayList<>();
        for (ProductInLog productInfo : this.productInLogs) {
            productsInfo.add("name : " + productInfo.productName
                    + "\nid : " + productInfo.productId
                    + "\nseller username : " + productInfo.sellerUsername
                    + "\nnumber : " + productInfo.number);
        }

        return productsInfo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogType() {
        return logType;
    }

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

    public String getCustomerRequest() {
        return customerRequest;
    }

    public void setCustomerRequest(String customerRequest) {
        this.customerRequest = customerRequest;
    }

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public ArrayList<ProductInLog> getProductInLogs() {
        return productInLogs;
    }

    public String getProduct(int index) {
        ProductInLog temp = productInLogs.get(index);
        return temp.productName + "(" + temp.productId + ")   " + temp.number;
    }

    public int productsNumber() {
        return productInLogs.size();
    }

    private static class ProductInLog {
        private String sellerUsername;
        private int number;
        private String productName;
        private String productId;

        private ProductInLog() {
        }

        public String getSellerUsername() {
            return sellerUsername;
        }

        public int getNumber() {
            return number;
        }
    }
}
