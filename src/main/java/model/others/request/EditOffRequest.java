package model.others.request;

import model.discount.Off;
import model.others.Date;
import model.others.Product;
import model.send.receive.RequestInfo;

import java.util.ArrayList;

public class EditOffRequest extends MainRequest {
    private Off off;
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
        switch (field) {
            case "start-time":
                editStartTime();
                break;
            case "finish-time":
                editFinishTime();
                break;
            case "percent":
                editPercent();
                break;
            case "products":
                editProducts(type);
                break;
        }

    }

    @Override
    boolean update(String type) {
        switch (field) {
            case "start-time":
            case "finish-time":
            case "percent":
            case "products":
                return true;
        }
        return false;
    }

    private void editProducts(String type) {
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
                        product.addOff(off);
                    }
                }
                break;
            case "edit-off replace":
                for (Product product : off.getProducts()) {
                    product.removeOff(off);
                }
                off.setProducts(products);
                for (Product product : products) {
                    product.addOff(off);
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

    private void editPercent() {
        off.setDiscountPercent(Integer.parseInt(newValue));
    }

    private void editFinishTime() {
        off.setDiscountFinishTime(Date.getDateWithString(newValue));
    }

    private void editStartTime() {
        off.setDiscountStartTime(Date.getDateWithString(newValue));
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void setOff(Off off) {
        this.off = off;
    }

    public ArrayList<String> getNewValueArrayList() {
        return newValueArrayList;
    }

    public void setNewValueArrayList(ArrayList<String> newValueArrayList) {
        this.newValueArrayList = newValueArrayList;
    }
}
