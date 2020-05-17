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

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public void setOffStatus(String offStatus) {
        this.offStatus = offStatus;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
}
