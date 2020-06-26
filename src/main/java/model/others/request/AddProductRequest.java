package model.others.request;

import com.google.gson.Gson;
import model.category.Category;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.others.Product;
import model.others.SpecialProperty;
import model.send.receive.RequestInfo;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddProductRequest extends MainRequest {
    private String sellerUsername;
    private String name;
    private double price;
    private int numberInStock;
    private String categoryName;
    private String subCategoryName;
    private String description;
    private String company;
    private ArrayList<SpecialProperty> properties;
    private byte[] file;
    private String fileExtension;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        String sellerUsername = this.sellerUsername;
        HashMap<String, String> addingInfo = new HashMap<>();
        addingInfo.put("Price: ", Double.toString(price));
        addingInfo.put("Name: ", name);
        addingInfo.put("Number-in-stock: ", Integer.toString(numberInStock));
        addingInfo.put("Main-category: ", categoryName);
        addingInfo.put("Sub-category: ", Objects.requireNonNullElse(subCategoryName, ""));
        addingInfo.put("Description: ", description);
        addingInfo.put("Company: ", company);
        for (int i = 1; i < properties.size()+1; i++) {
            addingInfo.put("Property " + i + "\n", properties.get(i - 1).toString());
        }
        requestInfo.setAddInfo("add-product", sellerUsername, addingInfo);
    }

    @Override
    void accept() {
        try {
            Seller seller = (Seller) Seller.getUserByUsername(sellerUsername);
            Product product = new Product();
            product.setSpecialProperties(properties);
            product.setDescription(description);
            product.setFile(file);
            product.setFileExtension(fileExtension);
            product.addSeller(seller, numberInStock, price);
            product.setMainCategory(Category.getMainCategoryByName(categoryName));
            if (subCategoryName != null) {
                product.setSubCategory(Category.getSubCategoryByName(subCategoryName));
            }
            product.setName(name);
            product.setCompany(company);
            product.getSellers().add(seller);
            product.setStatus("CONFIRMED");
            seller.addProduct(product);
            seller.updateDatabase().update();
            product.updateDatabase();
        } catch (CategoryDoesntExistException | UserNotExistException ignored) {
        }
    }

    @Override
    boolean update() {
        User seller = null;
        try {
            seller = User.getUserByUsername(sellerUsername);
        } catch (UserNotExistException ignored) {
        }

        if (!(seller instanceof Seller))
            return false;

        Category category;
        try {
            category = Category.getMainCategoryByName(categoryName);
        } catch (CategoryDoesntExistException e) {
            return false;
        }

        if (subCategoryName != null) {
            try {
                category = Category.getSubCategoryByName(subCategoryName);
            } catch (CategoryDoesntExistException e) {
                return false;
            }
        }

        for (SpecialProperty property : category.getSpecialProperties())
            if (!properties.contains(property)) properties.add(property);

        return true;
    }

    @Override
    public void decline() {

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

    public void setSpecialProperties(ArrayList<SpecialProperty> specialProperties) {
        this.properties = specialProperties;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
