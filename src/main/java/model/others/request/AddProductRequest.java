package model.others.request;

import com.google.gson.Gson;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Product;
import model.send.receive.RequestInfo;
import model.user.Seller;
import model.user.User;

import java.util.HashMap;
import java.util.Map;

public class AddProductRequest extends MainRequest {
    private String sellerUsername;
    private String name;
    private double price;
    private int numberInStock;
    private String categoryName;
    private String subCategoryName;
    private String description;
    private String company;
    private HashMap<String, String> specialProperties;

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        String sellerUsername = this.sellerUsername;
        HashMap<String, String> addingInfo = new HashMap<>();
        addingInfo.put("price", Double.toString(price));
        addingInfo.put("name", name);
        addingInfo.put("number-in-stock", Integer.toString(numberInStock));
        addingInfo.put("main-category", categoryName);
        if (subCategoryName != null)
            addingInfo.put("sub-category", subCategoryName);
        else
            addingInfo.put("sub-category", "");
        addingInfo.put("description", description);
        addingInfo.put("company", company);
        addingInfo.put("special-properties", (new Gson()).toJson(specialProperties));
        requestInfo.setAddInfo("add-product", sellerUsername, addingInfo);
    }

    @Override
    void accept(String type) {
        Seller seller = (Seller) Seller.getUserByUsername(sellerUsername);
        Product product = new Product();
        product.setSpecialProperties(specialProperties);
        product.setDescription(description);
        product.addSeller(seller, numberInStock, price);
        product.setMainCategory(Category.getMainCategoryByName(categoryName));
        if (subCategoryName != null) {
            product.setSubCategory(Category.getSubCategoryByName(subCategoryName));
        }
        product.setName(name);
        product.setCompany(company);
        product.getSellers().add(seller);
        seller.addProduct(product);
    }

    @Override
    boolean update(String type) {
        User seller = User.getUserByUsername(sellerUsername);
        if (!(seller instanceof Seller))
            return false;

        Category category = Category.getMainCategoryByName(categoryName);
        if (category == null)
            return false;

        if (subCategoryName != null) {
            category = Category.getSubCategoryByName(subCategoryName);
            if (category == null)
                return false;
        }
        for (String specialProperty : category.getSpecialProperties()) {
            if (!isThereProperty(specialProperty)) {
                specialProperties.put(specialProperty, "");
            }
        }
        return true;
    }

    private boolean isThereProperty(String key) {
        for (String entry : specialProperties.keySet()) {
            if (entry.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSpecialProperties(HashMap<String, String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
