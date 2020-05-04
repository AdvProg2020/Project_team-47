package model.others.request;

import com.google.gson.Gson;
import model.discount.Off;
import model.others.Date;
import model.others.Product;
import model.send.receive.RequestInfo;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class AddOffRequest extends MainRequest {
    private Date startTime;
    private Date finishTime;
    private String sellerUsername;
    private ArrayList<String> productsId;
    private int percent;


    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        Gson gson = new Gson();
        HashMap<String, String> addingInfo = new HashMap<>();
        addingInfo.put("percent", Integer.toString(percent));
        addingInfo.put("start-time", startTime.toString());
        addingInfo.put("finish-time", finishTime.toString());
        addingInfo.put("seller", sellerUsername);
        addingInfo.put("products-id", gson.toJson(productsId));
        requestInfo.setAddInfo("add-off", sellerUsername, addingInfo);
    }

    @Override
    void accept(String type) {
        Off off = new Off();
        Seller seller = (Seller) User.getUserByUsername(sellerUsername);
        off.setSeller(seller);
        if (startTime.before(Date.getCurrentDate())) {
            off.setDiscountStartTime(Date.getNextHour());
        } else {
            off.setDiscountStartTime(startTime);
        }
        off.setDiscountFinishTime(finishTime);
        off.setDiscountPercent(percent);
        ArrayList<Product> products = new ArrayList<>();
        for (String id : productsId) {
            Product product = seller.getProductById(id);
            if (product != null) {
                product.addOff(off);
                off.addProduct(product);
            }
        }
    }

    @Override
    boolean update(String type) {
        return User.isThereSeller(sellerUsername);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public ArrayList<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(ArrayList<String> productsId) {
        this.productsId = productsId;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
