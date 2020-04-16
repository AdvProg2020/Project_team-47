package model.category;

import model.others.Product;

import java.util.ArrayList;

abstract public class Category {
    private static ArrayList<Category> allMainCategories;
    private static ArrayList<Category> allSubCategories;
    protected String name;
    protected ArrayList<String> specialProperties;
    protected ArrayList<Product> allProducts;
    protected ArrayList<Category> subCategories;


    public Category() {
    }

    abstract public String categoryInfoForSending();



    public static String getAllCategoriesInfo(String sortField,String sortDirection){return null;}
    public static void removeCategory(String categoryName){}
    public static Category getCategoryByName(String name){return null;}



    public static boolean isThereMainCategory(String categoryName){return true;}
    public static boolean isThereSubCategory(String categoryName){return true;}





    public boolean isThereProduct(Product product) {return true;}
    public void addProduct(Product product){}
    public void addSpecialProperties(String properties){}
    public void removeSpecialProperties(String properties){}


    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialProperties() {
        return specialProperties;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialProperties(ArrayList<String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }
}
