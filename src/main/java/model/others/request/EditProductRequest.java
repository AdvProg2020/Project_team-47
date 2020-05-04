package model.others.request;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Product;
import model.send.receive.RequestInfo;
import model.user.Seller;

import java.util.HashMap;
import java.util.Map;

public class EditProductRequest extends MainRequest {
    private String field;
    private Object newValue;
    private HashMap<String, String> newValueHashMap;
    private Product product;
    private Seller seller;


    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        if (newValue instanceof String) {
            requestInfo.setEditInfo(field, (String) newValue, newValueHashMap);
        } else if (newValue instanceof Category) {
            requestInfo.setEditInfo(field, ((Category) newValue).getName(), newValueHashMap);
        }
    }

    @Override
    void accept(String type) {
        switch (field) {
            case "name":
                product.setName((String) newValue);
                break;
            case "price":
                product.changePrice(seller, Double.parseDouble((String) newValue));
                break;
            case "number-of-product":
                product.changeNumberOfProduct(seller, Integer.parseInt((String) newValue));
                break;
            case "category":
                product.setMainCategory((MainCategory) newValue);
                product.setSubCategory(null);
                break;
            case "sub-category":
                SubCategory subCategory = (SubCategory) newValue;
                product.setSubCategory(subCategory);
                product.setMainCategory(subCategory.getMainCategory());
                break;
            case "special-properties":
                changeSpecialProperties(type);
                break;
            case "description":
                product.setDescription((String) newValue);
                break;
        }
    }

    @Override
    boolean update(String type) {
        if (seller.hasProduct(product)) {
            return false;
        } else if (!Seller.isThereSeller(seller)) {
            return false;
        }
        switch (field) {
            case "name":
            case "price":
            case "number-of-product":
            case "description":
                return true;
            case "category":
                return updateForCategory("main-category");
            case "sub-category":
                return updateForCategory("sub-category");
            case "specialProperties":
                return updateProperties(type);
        }
        return false;
    }

    private boolean updateProperties(String type) {
        Category category = product.getSubCategory();
        if (category == null) {
            category = product.getMainCategory();
        }
        switch (type) {
            case "edit-product append":
            case "edit-product change":
                return true;
            case "edit-product replace":
                for (String specialProperty : category.getSpecialProperties()) {
                    if (!newValueHashMap.containsKey(specialProperty)) {
                        newValueHashMap.put(specialProperty, product.getSpecialProperties().get(specialProperty));
                    }
                }
                return true;
            case "edit-product remove":
                for (String specialProperty : category.getSpecialProperties()) {
                    newValueHashMap.remove(specialProperty);
                }
                return true;
        }
        return false;
    }

    private boolean updateForCategory(String categoryType) {
        if (categoryType.equals("main-category") && !Category.isThereMainCategory((MainCategory) newValue)) {
            return false;
        } else if (categoryType.equals("sub-category") && !Category.isThereSubCategory((SubCategory) newValue)) {
            return false;
        }
        for (String specialProperty : ((Category) newValue).getSpecialProperties()) {
            if (!product.getSpecialProperties().containsKey(specialProperty)) {
                product.getSpecialProperties().put(specialProperty, "");
            }
        }
        return true;
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

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
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
