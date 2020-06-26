package model.category;

import controller.Controller;
import database.CategoryData;
import database.Database;
import model.ecxeption.product.CategoryDoesntExistException;
import model.others.Product;
import model.others.Sort;
import model.others.SpecialProperty;
import model.send.receive.CategoryInfo;

import java.util.ArrayList;

abstract public class Category {
    static ArrayList<MainCategory> allMainCategories;
    static ArrayList<SubCategory> allSubCategories;

    static {
        allMainCategories = new ArrayList<>();
        allSubCategories = new ArrayList<>();
    }

    protected String name;
    protected ArrayList<SpecialProperty> specialProperties;
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
        try {
            MainCategory mainCategory = getMainCategoryByName(categoryName);
            mainCategory.remove();
        } catch (CategoryDoesntExistException ignored) {
        }
    }

    public static void removeSubCategory(String categoryName) {
        try {
            SubCategory subCategory = getSubCategoryByName(categoryName);
            subCategory.remove();
        } catch (CategoryDoesntExistException ignored) {
        }
    }

    public static SubCategory getSubCategoryByName(String name) throws CategoryDoesntExistException {
        for (SubCategory subCategory : allSubCategories) {
            if (subCategory.name.equalsIgnoreCase(name))
                return subCategory;
        }
        throw new CategoryDoesntExistException();
    }

    public static MainCategory getMainCategoryByName(String name) throws CategoryDoesntExistException {
        for (MainCategory mainCategory : Category.allMainCategories) {
            if (mainCategory.name.equalsIgnoreCase(name)) {
                return mainCategory;
            }
        }
        throw new CategoryDoesntExistException();
    }

    public static boolean isThereMainCategory(String categoryName) {
        for (MainCategory mainCategory : Category.allMainCategories) {
            if (mainCategory.getName().equalsIgnoreCase(categoryName)) {
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

    public void addNumericProperty(String property, String unit) {
        specialProperties.add(new SpecialProperty(property, -1, unit));
        this.updateDatabase();
    }

    public void addTextProperty(String property) {
        specialProperties.add(new SpecialProperty(property, null));
        this.updateDatabase();
    }

    public void removeSpecialProperties(String property) {
        SpecialProperty temp = new SpecialProperty(property);
        specialProperties.remove(temp);
        this.updateDatabase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SpecialProperty> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(ArrayList<SpecialProperty> specialProperties) {
        this.specialProperties = specialProperties;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void removeFromDatabase() {
        Database.removeCategory(this.id);
    }
}
