package model.others.request;

import model.category.Category;
import model.others.Product;
import model.send.receive.RequestInfo;
import model.user.Seller;

import java.util.HashMap;
import java.util.Map;

public class EditProductRequest extends MainRequest {
    private String field;
    private String newValue;
    private HashMap<String, String> newValueHashMap;
    private Product product;
    private Seller seller;


    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setEditInfo(field, newValue, newValueHashMap);
    }

    @Override
    void accept(String type) {
        switch (field) {
            case "name":
                product.setName(newValue);
                break;
            case "price":
                product.changePrice(seller, Double.parseDouble(newValue));
                break;
            case "number-of-product":
                product.changeNumberOfProduct(seller, Integer.parseInt(newValue));
                break;
            case "category":
                product.setCategory(Category.getCategoryByName(newValue));
            case "special-properties":
                changeSpecialProperties(type);
                break;
            case "description":
                product.setDescription(newValue);
                break;
        }
    }

    private void changeSpecialProperties(String type) {
        switch (type) {
            case "edit-product append":
                appendSpecialProperties();
                break;
            case "edit-product remove":
                removeSpecialProperties();
                break;
            case "edit-product replace":
                product.setSpecialProperties(newValueHashMap);
                break;
            case "edit-product change":
                removeSpecialProperties();
                appendSpecialProperties();
                break;

        }
    }

    private void appendSpecialProperties() {
        for (Map.Entry<String, String> entry : this.newValueHashMap.entrySet()) {
            product.getSpecialProperties().put(entry.getKey(), entry.getValue());
        }
    }

    private void removeSpecialProperties() {
        for (Map.Entry<String, String> entry : this.newValueHashMap.entrySet()) {
            product.getSpecialProperties().remove(entry.getKey());
        }
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

    public HashMap<String, String> getNewValueHashMap() {
        return newValueHashMap;
    }

    public void setNewValueHashMap(HashMap<String, String> newValueHashMap) {
        this.newValueHashMap = newValueHashMap;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
