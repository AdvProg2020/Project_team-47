package model.log;


import database.Database;
import model.ecxeption.user.UserNotExistException;
import model.others.Product;
import model.send.receive.LogInfo;
import model.send.receive.ProductInfo;
import model.send.receive.UserInfo;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyLog extends Log {
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String customerRequests;
    private final ArrayList<ProductInLog> products;
    private double appliedDiscount;


    public BuyLog() {
        super();
        products = new ArrayList<>();
        this.purchaseStatus = "IN_SENDING_QUEUE";
    }

    @Override
    public void updateDatabase() {
        Database.addBuyLog(this, this.logId);
    }

    @Override
    public LogInfo getLogInfoForSending() {
        LogInfo logInfo = new LogInfo(logId, logDate, "buy log");
        logInfo.setAddress(address);
        logInfo.setAppliedDiscount(appliedDiscount);
        logInfo.setCustomerRequest(customerRequests);
        logInfo.setPhoneNumber(phoneNumber);
        logInfo.setPostalCode(postalCode);
        logInfo.setPrice(price);
        logInfo.setCustomer(customer.getUsername());
        logInfo.setStatus(purchaseStatus);
        for (ProductInLog productInLog : this.products) {
            logInfo.addProduct(productInLog.seller, productInLog.product, productInLog.number);
        }
        return logInfo;
    }

    @Override
    public boolean isThereProductInLog(String productId) {
        for (ProductInLog productInLog : this.products) {
            if (productInLog.product.getId().equalsIgnoreCase(productId)) {
                return true;
            }
        }
        return false;
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

    public void setCustomerRequests(String customerRequests) {
        this.customerRequests = customerRequests;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public void addProduct(Seller seller, Product product, int number) {
        ProductInLog productInLog = new ProductInLog();
        productInLog.product = product.getProductInfo();
        productInLog.seller = seller.userInfoForSending();
        productInLog.number = number;
        this.products.add(productInLog);
    }

    public void createSellLog() {
        HashMap<String, SellLog> sellerBuyLogHashMap = new HashMap<>();
        for (ProductInLog productInLog : this.products) {
            String username = productInLog.seller.getUsername();
            SellLog sellLog;
            if (sellerBuyLogHashMap.containsKey(username)) {
                sellLog = sellerBuyLogHashMap.get(username);
                sellLog.addProduct(productInLog.product);
            } else {
                sellLog = new SellLog(productInLog.seller);
                sellLog.customer = this.customer;
                sellLog.addProduct(productInLog.product);
                sellLog.purchaseStatus = this.purchaseStatus;
                sellerBuyLogHashMap.put(username, sellLog);
            }
        }

        updateSellersLog(sellerBuyLogHashMap);
    }

    private void updateSellersLog(HashMap<String, SellLog> sellerBuyLogHashMap) {
        for (Map.Entry<String, SellLog> entry : sellerBuyLogHashMap.entrySet()) {
            User seller = null;
            try {
                seller = User.getUserByUsername(entry.getKey());
            } catch (UserNotExistException e) {
                e.printStackTrace();
                continue;
            }
            if (seller instanceof Seller) {
                ((Seller) seller).addSellLog(entry.getValue());
                seller.updateDatabase().update();
                entry.getValue().updateDatabase();
            }
        }
    }


    private static class ProductInLog {
        private ProductInfo product;
        private UserInfo seller;
        private int number;
    }

}
