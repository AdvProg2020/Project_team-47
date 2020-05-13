package model.others.request;

import model.category.Category;
import model.category.SubCategory;
import model.others.Product;
import model.send.receive.RequestInfo;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProductRequest extends MainRequest {
    private String field;
    private String newValue;
    private HashMap<String, String> newValueHashMap;
    private String productId;
    private String sellerUsername;

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        if (newValue != null) {
            requestInfo.setEditInfo(field, newValue, newValueHashMap);
        }
    }

    @Override
    void accept(String type) {
        Product product = Product.getProductWithId(productId);
        Seller seller = (Seller) Seller.getUserByUsername(sellerUsername);
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
                product.setMainCategory(Category.getMainCategoryByName(newValue));
                product.setSubCategory(null);
                break;
            case "sub-category":
                SubCategory subCategory = Category.getSubCategoryByName(newValue);
                product.setSubCategory(subCategory);
                product.setMainCategory(subCategory.getMainCategory());
                break;
            case "special-properties":
                changeSpecialProperties(type,product);
                break;
            case "description":
                product.setDescription(newValue);
                break;
        }
    }

    @Override
    boolean update(String type) {
        Seller seller;
        User user = Seller.getUserByUsername(sellerUsername);
        if (!(user instanceof Seller)) {
            return false;
        }else
            seller = (Seller) user;
        Product product = Product.getProductWithId(productId);
        if(product==null)
            return false;

        if (!seller.hasProduct(product)||!Seller.isThereSeller(seller)) {
            return false;
        }

        switch (field) {
            case "name":
            case "price":
            case "number-of-product":
            case "description":
                return true;
            case "category":
                return updateForCategory("main-category",product);
            case "sub-category":
                return updateForCategory("sub-category",product);
            case "specialProperties":
                return updateProperties(type);
        }
        return false;
    }

    private boolean updateProperties(String type) {
        Product product = Product.getProductWithId(productId);
        if(product == null)
            return false;
        Category category = product.getSubCategory();
        if (category == null) {
            category = product.getMainCategory();
        }

        switch (type) {
            case "edit-product append":
            case "edit-product change":
                return true;
            case "edit-product replace":
                updatePropertiesReplace(category,product);
                return true;
            case "edit-product remove":
                updatePropertiesRemove(category);
                return true;
        }
        return false;
    }

    private void updatePropertiesReplace(Category category,Product product) {
        for (String specialProperty : category.getSpecialProperties()) {
            if (!isThereProperty(specialProperty)) {
                newValueHashMap.put(specialProperty, getProductProperty(product, specialProperty));
            }
        }
    }

    private void updatePropertiesRemove(Category category) {
        for (String specialProperty : category.getSpecialProperties()) {
            for (Map.Entry<String, String> entry : newValueHashMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(specialProperty)) {
                    newValueHashMap.remove(entry.getKey());
                    break;
                }
            }
        }
    }

    private String getProductProperty(Product product,String key) {
        for (Map.Entry<String, String> entry : product.getSpecialProperties().entrySet()) {
            if(entry.getKey().equalsIgnoreCase(key))
                return entry.getValue();
        }
        return "";
    }

    private boolean updateForCategory(String categoryType,Product product) {
        if (categoryType.equals("main-category")) {
            Category category=Category.getMainCategoryByName(newValue);
            if(category==null)
                return false;
            for (String specialProperty : category.getSpecialProperties()) {
                if (!productHasProperty(product,specialProperty)) {
                    product.getSpecialProperties().put(specialProperty, "");
                }
            }
        } else if (categoryType.equals("sub-category") && !Category.isThereSubCategory(newValue)) {
            Category category=Category.getSubCategoryByName(newValue);
            if(category==null)
                return false;
            for (String specialProperty : category.getSpecialProperties()) {
                if (!productHasProperty(product,specialProperty)) {
                    product.getSpecialProperties().put(specialProperty, "");
                }
            }
        }

        return true;
    }

    private boolean isThereProperty(String property) {
        for (Map.Entry<String, String> entry : newValueHashMap.entrySet()) {
            if(entry.getKey().equalsIgnoreCase(property))
                return true;
        }
        return false;
    }

    private static boolean productHasProperty(Product product, String property) {
        for (Map.Entry<String, String> entry : product.getSpecialProperties().entrySet()) {
            if(entry.getKey().equalsIgnoreCase(property))
                return true;
        }
        return false;
    }

    private void changeSpecialProperties(String type,Product product) {
        switch (type) {
            case "edit-product append":
                appendSpecialProperties(product);
                break;
            case "edit-product remove":
                removeSpecialProperties(product);
                break;
            case "edit-product replace":
                product.setSpecialProperties(newValueHashMap);
                break;
            case "edit-product change":
                removeSpecialProperties(product);
                appendSpecialProperties(product);
                break;
        }
    }

    private void appendSpecialProperties(Product product) {
        product.getSpecialProperties().putAll(newValueHashMap);
    }

    private void removeSpecialProperties(Product product) {
        ArrayList<String> foundKeys = new ArrayList<>();
        for (Map.Entry<String, String> entry : product.getSpecialProperties().entrySet()) {
            if (isThereProperty(entry.getKey())) {
                foundKeys.add(entry.getKey());
            }
        }
        for (String foundKey : foundKeys) {
            product.getSpecialProperties().remove(foundKey);
        }
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void setNewValueHashMap(HashMap<String, String> newValueHashMap) {
        this.newValueHashMap = newValueHashMap;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSeller(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
}
