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
    }

    public static void removeSubCategory(String categoryName) {
    }

    public static SubCategory getSubCategoryByName(String name) {
        return null;
    }

    public static MainCategory getMainCategoryByName(String name) {
        return null;
    }

    public static boolean isThereMainCategory(String categoryName) {
        return true;
    }

    public static boolean isThereMainCategory(MainCategory mainCategory) {
        return true;
    }

    public static boolean isThereSubCategory(String categoryName) {
        return true;
    }

    public static boolean isThereSubCategory(SubCategory subCategory) {
        return true;
    }

    public static boolean isThereCategory(String name) {
        return false;
    }

    public abstract CategoryInfo categoryInfoForSending();

    public boolean isThereProduct(Product product) {
        return true;
    }

    public void addProduct(Product product) {
    }

    public void addSpecialProperties(String properties) {
    }

    public void removeSpecialProperties(String properties) {
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
