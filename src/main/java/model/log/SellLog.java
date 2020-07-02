package model.log;

import database.Database;
import model.send.receive.LogInfo;
import model.send.receive.ProductInfo;
import model.send.receive.UserInfo;

import java.util.ArrayList;

public class SellLog extends Log {
    private final UserInfo seller;
    private final ArrayList<ProductInLog> products;
    private String address;
    private String postalCode;
    private String phoneNumber;

    public SellLog(UserInfo seller) {
        this.seller = seller;
        products = new ArrayList<>();
    }

    public UserInfo getSeller() {
        return seller;
    }

    public ArrayList<ProductInLog> getProducts() {
        return products;
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

    @Override
    public void updateDatabase() {
        Database.addSellLog(this, this.logId);
    }

    @Override
    public LogInfo getLogInfoForSending() {
        LogInfo logInfo = new LogInfo(logId, logDate, "sell log");
        logInfo.setPrice(price);
        logInfo.setCustomer(customer.getUsername());
        logInfo.setSeller(seller.getUsername());
        logInfo.setStatus(purchaseStatus);
        logInfo.setAddress(this.address);
        logInfo.setPhoneNumber(this.phoneNumber);
        logInfo.setPostalCode(this.postalCode);
        for (ProductInLog productInLog : products) {
            logInfo.addProduct(seller, productInLog.productInfo, productInLog.number);
        }
        return logInfo;
    }

    @Override
    public boolean isThereProductInLog(String productId) {
        for (ProductInLog productInLog : this.products) {
            if (productId.equalsIgnoreCase(productInLog.productInfo.getId())) {
                return true;
            }
        }
        return false;
    }

    private ProductInLog getProductInLog(String productId) {
        for (ProductInLog product : this.products) {
            if (productId.equals(product.productInfo.getId())) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(ProductInfo productInfo, int number) {
        this.setPrice(getPrice() + number * productInfo.getFinalPrice(seller.getUsername()));
        ProductInLog productInLog = getProductInLog(productInfo.getId());
        if (productInLog == null) {
            productInLog = new ProductInLog();
            productInLog.productInfo = productInfo;
            this.products.add(productInLog);
        } else {
            productInLog.number++;
        }
    }

    private static class ProductInLog {
        private ProductInfo productInfo;
        private int number;
    }
}
