package model.send.receive;

import model.others.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryInfo {
    private String name;
    private ArrayList<String> specialProperties;
    private HashMap<String, String> productsNameId;
    private ArrayList<String> subCategories;
    private String mainCategory;

    public CategoryInfo(String name, ArrayList<String> properties) {
        this.name = name;
        this.specialProperties = properties;
        this.productsNameId = new HashMap<>();
    }

    public void addProduct(Product product) {
        this.productsNameId.put(product.getName(), product.getId());
    }

    public void setSubCategories(ArrayList<String> subCategories) {
        this.subCategories = subCategories;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }
}
