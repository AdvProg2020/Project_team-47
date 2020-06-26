package model.others.request;

import com.google.gson.Gson;
import controller.Controller;
import model.discount.Off;
import model.ecxeption.user.UserNotExistException;
import model.others.Product;
import model.send.receive.RequestInfo;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddOffRequest extends MainRequest {
    private Date startTime;
    private Date finishTime;
    private String sellerUsername;
    private ArrayList<String> productsId;
    private int percent;

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        HashMap<String, String> addingInfo = new HashMap<>();
        addingInfo.put("Percent: ", Integer.toString(percent));
        addingInfo.put("Start-time: ", startTime.toString());
        addingInfo.put("Finish-time: ", finishTime.toString());
        addingInfo.put("Seller: ", sellerUsername);
        for (int i = 1; i < productsId.size()+1; i++) {
            addingInfo.put("Product " + i+": ", productsId.get(i - 1));
        }
        requestInfo.setAddInfo("add-off", sellerUsername, addingInfo);
    }

    @Override
    void accept() {
        Off off = new Off();
        Seller seller;
        try {
            seller = (Seller) User.getUserByUsername(sellerUsername);
        } catch (UserNotExistException ignored) {
            return;
        }

        off.setSeller(seller);
        if (startTime.before(Controller.getCurrentTime())) {
            off.setStartTime(Controller.getCurrentTime());
        } else {
            off.setStartTime(startTime);
        }
        off.setFinishTime(finishTime);
        off.setPercent(percent);
        seller.addOff(off);
        for (String id : productsId) {
            Product product = seller.getProductById(id);
            if (product != null) {
                product.addOff(off, seller);
                off.addProduct(product);
                product.updateDatabase();
            }
        }
        seller.updateDatabase().update();
        off.updateDatabase();
    }

    @Override
    boolean update() {
        if (finishTime.before(Controller.getCurrentTime()))
            return false;
        try {
            return (User.getUserByUsername(sellerUsername) instanceof Seller);
        } catch (UserNotExistException e) {
            return false;
        }
    }

    @Override
    public void decline() {

    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public void setProductsId(ArrayList<String> productsId) {
        this.productsId = productsId;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

}
