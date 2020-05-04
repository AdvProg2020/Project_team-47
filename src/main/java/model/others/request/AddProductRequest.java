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

public class AddProductRequest extends MainRequest {
    private User seller;
    private String name;
    private double price;
    private int numberInStock;
    private MainCategory category;
    private SubCategory subCategory;
    private String description;
    private String company;
    private HashMap<String, String> specialProperties;

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        String sellerUsername = seller.getUsername();
        Gson gson = new Gson();
        HashMap<String, String> addingInfo = new HashMap<>();
        addingInfo.put("price", Double.toString(price));
        addingInfo.put("name", name);
        addingInfo.put("number-in-stock", Integer.toString(numberInStock));
        addingInfo.put("main-category", category.getName());
        if (subCategory != null)
            addingInfo.put("sub-category", subCategory.getName());
        else
            addingInfo.put("sub-category", "");
        addingInfo.put("description", description);
        addingInfo.put("company", company);
        addingInfo.put("special-properties", gson.toJson(specialProperties));
        requestInfo.setAddInfo("add-product", sellerUsername, addingInfo);
    }

    @Override
    void accept(String type) {
        Product product = new Product();
        product.setSpecialProperties(specialProperties);
        product.setDescription(description);
        product.addSeller((Seller) seller, numberInStock, price);
        product.setMainCategory(category);
        if (subCategory != null)
            product.setSubCategory(subCategory);
        product.setName(name);
        product.setCompany(company);
        product.getSellers().add((Seller) seller);
    }

    @Override
    boolean update(String type) {
        if (!Category.isThereMainCategory(category) && !(
                subCategory == null || Category.isThereSubCategory(subCategory)
        )) {
            return false;
        }
        Category tempCategory = subCategory;
        if (tempCategory == null) tempCategory = category;
        for (String specialProperty : tempCategory.getSpecialProperties()) {
            if (!specialProperties.containsKey(specialProperty)) {
                specialProperties.put(specialProperty, "");
            }
        }
        return true;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
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

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(MainCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public HashMap<String, String> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(HashMap<String, String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
