package model.others.request;

import com.google.gson.Gson;
import model.category.Category;
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
    private Category category;
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
        addingInfo.put("category", category.getName());
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
        product.getNumberOfProductSellerHas().put(seller, numberInStock);
        product.setCategory(category);
        product.setName(name);
        product.getPrice().put((Seller) seller, price);
        product.setCompany(company);
        product.getSellers().add((Seller) seller);
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

    public void setCategory(Category category) {
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
}
