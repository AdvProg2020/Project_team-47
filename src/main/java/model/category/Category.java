package model.category;

import controller.Controller;
import database.CategoryData;
import database.Database;
import model.others.Product;
import model.others.Sort;
import model.send.receive.CategoryInfo;

import java.util.ArrayList;
import java.util.Iterator;

abstract public class Category {
    static ArrayList<MainCategory> allMainCategories;
    static ArrayList<SubCategory> allSubCategories;

    static {
        allMainCategories = new ArrayList<>();
        allSubCategories = new ArrayList<>();
    }

    protected String name;
    protected ArrayList<String> specialProperties;
    protected ArrayList<Product> allProducts;
    private String id;

    public Category() {
        allProducts = new ArrayList<>();
        specialProperties = new ArrayList<>();
        this.id = idCreator();
    }

    private static String idCreator() {
        String id = Controller.idCreator();
        if (isThereCategoryWithId(id)) {
            return idCreator();
        } else
            return id;
    }

    private static boolean isThereCategoryWithId(String id) {
        for (Category mainCategory : allMainCategories) {
            if (mainCategory.id.equals(id)) {
                return true;
            }
        }
        for (Category subCategory : allSubCategories) {
            if (subCategory.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereCategory(String name) {
        return isThereSubCategory(name) || isThereMainCategory(name);
    }

    public static ArrayList<CategoryInfo> getAllCategoriesInfo() {
        ArrayList<CategoryInfo> categoriesInfo = new ArrayList<>();
        for (MainCategory mainCategory : allMainCategories) {
            categoriesInfo.add(mainCategory.categoryInfoForSending());
        }
        return categoriesInfo;
    }

    public static ArrayList<CategoryInfo> getAllCategoriesInfo(String sortField, String sortDirection) {
        ArrayList<CategoryInfo> categoriesInfo = new ArrayList<>();
        ArrayList<MainCategory> sortedCategories = Sort.sortMainCategories(sortField, sortDirection, allMainCategories);
        for (MainCategory mainCategory : sortedCategories) {
            categoriesInfo.add(mainCategory.categoryInfoForSending());
        }
        return categoriesInfo;
    }

    public static void removeMainCategory(String categoryName) {
        MainCategory mainCategory = getMainCategoryByName(categoryName);
        if (mainCategory == null)
            return;
        mainCategory.remove();
    }

    public static void removeSubCategory(String categoryName) {
        SubCategory subCategory = getSubCategoryByName(categoryName);
        if (subCategory == null)
            return;
        subCategory.remove();
    }

    public static SubCategory getSubCategoryByName(String name) {
        for (SubCategory subCategory : allSubCategories) {
            if (subCategory.name.equalsIgnoreCase(name))
                return subCategory;
        }
        return null;
    }

    public static MainCategory getMainCategoryByName(String name) {
        for (MainCategory mainCategory : Category.allMainCategories) {
            if (mainCategory.name.equalsIgnoreCase(name)) {
                return mainCategory;
            }
        }
        return null;
    }

    public static boolean isThereMainCategory(String categoryName) {
        for (MainCategory allMainCategory : Category.allMainCategories) {
            if (allMainCategory.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereSubCategory(String categoryName) {
        for (SubCategory allSubCategory : Category.allSubCategories) {
            if (allSubCategory.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public abstract void updateDatabase();

    void updateDatabase(CategoryData categoryData) {
        categoryData.setId(this.id);
        categoryData.setName(this.name);
        categoryData.setSpecialProperties(this.specialProperties);
        for (Product product : allProducts) {
            categoryData.addProduct(product.getId());
        }
    }

    public abstract void remove();

    public abstract CategoryInfo categoryInfoForSending();

    public boolean isThereProduct(Product product) {
        return allProducts.contains(product);
    }

    public void addProduct(Product product) {
        allProducts.add(product);
        this.updateDatabase();
    }

    public void addSpecialProperties(String properties) {
        specialProperties.add(properties);
        this.updateDatabase();
    }

    public void removeSpecialProperties(String properties) {
        Iterator<String> iterator = specialProperties.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equalsIgnoreCase(properties)) {
                iterator.remove();
                break;
            }
        }
        this.updateDatabase();
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


    public void setId(String id) {
        this.id = id;
    }

    public void removeFromDatabase() {
        Database.removeCategory(this.id);
    }
}
