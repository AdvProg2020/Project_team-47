package model.category;

import model.others.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class Category {
    private static ArrayList<SubCategory> subCategories;
    private String name;
    private HashMap<String, String> properties;
    private ArrayList<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public static boolean doesSubCategoryExist(String name) {
        Iterator<SubCategory> iterator = Category.subCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    protected static void addSubCategory(SubCategory subCategory) {
        Category.subCategories.add(subCategory);
    }

    public static void removeSubCategory(String name) {
        Iterator<SubCategory> iterator = Category.subCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                iterator.remove();
                break;
            }
        }
    }

    public static SubCategory getSubCategory(String name) {
        Iterator<SubCategory> iterator = Category.subCategories.iterator();
        while (iterator.hasNext()) {
            SubCategory subCategory = iterator.next();
            if (subCategory.getName().equalsIgnoreCase(name)) {
                return SubCategory;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * each category holds properties in format of title-info
     * 
     * @param title the title of information.
     * @param info  this String-kind details are just provided for displaying and
     *              uses of manager.
     *              Each info is related with only one title.
     *              i.e: for foods -->
     *              vegeteble: day order just
     *              meal: foods with expiry date
     *              .
     *              .
     *              .
     */
    public void addProperty(String title, String info) {
        properties.put(title, info);
    }

    public void removeProperty(String title) {
        properties.remove(title);
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public boolean doesProductExist(Product product) {
        return products.contains(product);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Product getProduct(String name) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
