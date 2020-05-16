package model.category;

import model.others.Product;
import model.send.receive.CategoryInfo;

import java.util.ArrayList;

abstract public class Category {
    protected static ArrayList<Category> allMainCategories;
    protected static ArrayList<Category> allSubCategories;
    protected String name;
    protected ArrayList<String> specialProperties;
    protected ArrayList<Product> allProducts;

    static{
        allMainCategories = new ArrayList<>();
        allSubCategories=new ArrayList<>();
    }
    public Category() {
        allProducts = new ArrayList<>();
        specialProperties = new ArrayList<>();
    }

    public static ArrayList<CategoryInfo> getAllCategoriesInfo() {
        return null;
    }

    public static ArrayList<CategoryInfo> getAllCategoriesInfo(String sortField, String sortDirection) {
        return null;
    }

    public static void removeMainCategory(String categoryName) {
        Iterator<MainCategory> iterator = Category.allMainCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(categoryName)) {
                iterator.remove();
                break;
            }
        }
    }

    public static void removeSubCategory(String categoryName) {
        Iterator<SubCategory> iterator = Category.allSubCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(categoryName)) {
                iterator.remove();
                break;
            }
        }
    }

    public static SubCategory getSubCategoryByName(String name) {
        Iterator<SubCategory> iterator = SubCategory.allSubCategories.iterator();
        while (iterator.hasNext()) {
            SubCategory subCategory = iterator.next();
            if (subCategory.getName().equalsIgnoreCase(name)) {
                return subCategory;
            }
        }
        return null;
    }

    public static MainCategory getMainCategoryByName(String name) {
        Iterator<MainCategory> iterator = Category.allMainCategories.iterator();
        while (iterator.hasNext()) {
            SubCategory mainCategory = iterator.next();
            if (mainCategory.getName().equalsIgnoreCase(name)) {
                return mainCategory;
            }
        }
    }

    public static boolean isThereMainCategory(String categoryName) {
        Iterator<MainCategory> iterator = Category.allMainCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereMainCategory(MainCategory mainCategory) {
        Iterator<MainCategory> iterator = Category.allMainCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(mainCategory)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereSubCategory(String categoryName) {
        Iterator<SubCategory> iterator = Category.allSubCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereSubCategory(SubCategory subCategory) {
        Iterator<SubCategory> iterator = Category.allSubCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(subCategory)) {
                return true;
            }
        }
        return false;
    }

    public abstract CategoryInfo categoryInfoForSending();

    public boolean isThereProduct(Product product) {
        return allProducts.contains(product);
    }

    public void addProduct(Product product) {
        allProducts.add(product);
    }

    public void addSpecialProperties(String properties) {
        specialProperties.add(properties);
    }

    public void removeSpecialProperties(String properties) {
        Iterator<String> iterator = specialProperties.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equalsIgnoreCase(properties)) {
                iterator.remove();
                break;
            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(ArrayList<String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }
}
