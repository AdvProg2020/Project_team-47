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

    private void editProducts(String type) {
        ArrayList<Product> products = new ArrayList<>();
        for (String s : newValueArrayList) {
            Product product = Product.getProductWithId(s);
            if (product != null) {
                products.add(product);
            }
        }
        switch (type) {
            case "edit-off append":
                off.getProducts().addAll(products);
                break;
            case "edit-off replace":
                off.setProducts(products);
                break;
            case "edit-off remove":
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
