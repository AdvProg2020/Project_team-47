package model.send.receive;

import model.others.Product;
import model.others.SpecialProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryInfo {
    private final String name;
    private final ArrayList<SpecialProperty> specialProperties;
    private final HashMap<String, String> productsNameId;
    private String type;
    private ArrayList<String> subCategories;
    private String mainCategory;

    public CategoryInfo(String name, ArrayList<SpecialProperty> properties) {
        this.name = name;
        this.specialProperties = properties;
        this.productsNameId = new HashMap<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addProduct(Product product) {
        this.productsNameId.put(product.getName(), product.getId());
    }

    public String getName() {
        return name;
    }

    public ArrayList<SpecialProperty> getSpecialProperties() {
        return specialProperties;
    }

    public HashMap<String, String> getProductsNameId() {
        return productsNameId;
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<String> subCategories) {
        this.subCategories = subCategories;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }
}
