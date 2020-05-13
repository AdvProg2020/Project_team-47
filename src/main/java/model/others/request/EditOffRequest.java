package model.others.request;

import model.discount.Off;
import model.others.Date;
import model.others.Product;
import model.send.receive.RequestInfo;

import java.util.ArrayList;

public class EditOffRequest extends MainRequest {
    private String offId;
    private String field;
    private String newValue;
    private ArrayList<String> newValueArrayList;

    public EditOffRequest() {
    }

    public EditOffRequest(String field, String newValue) {
        this.field = field;
        this.newValue = newValue;
    }

    public EditOffRequest(String field, ArrayList<String> newValueForProducts) {
        this.field = field;
        this.newValueArrayList = newValueForProducts;
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setEditInfo(field, newValue, newValueArrayList);
    }

    @Override
    void accept(String type) {
        Off off = Off.getOffById(offId);
        switch (field) {
            case "start-time":
                editStartTime(off);
                break;
            case "finish-time":
                editFinishTime(off);
                break;
            case "percent":
                editPercent(off);
                break;
            case "products":
                editProducts(type, off);
                break;
        }

    }

    @Override
    boolean update(String type) {
        if (!Off.isThereOff(offId))
            return false;
        switch (field) {
            case "start-time":
            case "finish-time":
            case "percent":
            case "products":
                return true;
        }
        return false;
    }

    private void editProducts(String type, Off off) {
        ArrayList<Product> products = new ArrayList<>();
        for (String id : newValueArrayList) {
            Product product = Product.getProductWithId(id);
            if (product != null) {
                products.add(product);
            }
        }
        switch (type) {
            case "edit-off append":
                for (Product product : products) {
                    if (!off.getProducts().contains(product)) {
                        off.getProducts().add(product);
                        product.addOff(off,off.getSeller());
                    }
                }
                break;
            case "edit-off replace":
                for (Product product : off.getProducts()) {
                    product.removeOff(off);
                }
                off.setProducts(products);
                for (Product product : products) {
                    product.addOff(off,off.getSeller());
                }
                break;
            case "edit-off remove":
                for (Product product : products) {
                    product.removeOff(off);
                }
                off.getProducts().removeAll(products);
                break;
        }
    }

    private void editPercent(Off off) {
        off.setDiscountPercent(Integer.parseInt(newValue));
    }

    private void editFinishTime(Off off) {
        off.setDiscountFinishTime(Date.getDateWithString(newValue));
    }

    private void editStartTime(Off off) {
        off.setDiscountStartTime(Date.getDateWithString(newValue));
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

}
