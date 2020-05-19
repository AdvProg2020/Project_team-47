package model.send.receive;

import model.others.Product;

import java.util.Date;
import java.util.HashMap;

public class OffInfo {
    private Date startTime;
    private Date finishTime;
    private int percent;
    private String offId;
    private String offStatus;
    private String sellerUsername;
    private HashMap<String, String> productsNameId;

    public OffInfo(Date startTime, Date finishTime, int percent) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.percent = percent;
        this.productsNameId = new HashMap<>();
    }

    public void addProduct(Product product) {
        this.productsNameId.put(product.getName(), product.getId());
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public int getPercent() {
        return percent;
    }

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public String getOffStatus() {
        return offStatus;
    }

    public void setOffStatus(String offStatus) {
        this.offStatus = offStatus;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public HashMap<String, String> getProductsNameId() {
        return productsNameId;
    }
}
