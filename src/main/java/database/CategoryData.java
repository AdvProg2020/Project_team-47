package database;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.others.Product;

import java.util.ArrayList;

public class CategoryData {
    private String type;
    private String name;
    private ArrayList<String> specialProperties;
    private ArrayList<String> productsId;
    private ArrayList<String> subCategoriesNames;
    private String mainCategoryName;
    private String id;

    public CategoryData(String type) {
        this.type = type;
        productsId = new ArrayList<>();
        if ("main-category".equals(type)) {
            subCategoriesNames = new ArrayList<>();
        }
    }

    static void addCategories(ArrayList<CategoryData> categories) {
        for (CategoryData category : categories) {
            category.createCategory();
        }
    }

    static void connectRelations(ArrayList<CategoryData> categories) {
        for (CategoryData category : categories) {
            category.connectRelations();
        }
    }

    private void connectRelations() {
        Category category;
        if (this.type.equals("main-category")) {
            category = Category.getMainCategoryByName(this.name);
            this.connectSubCategories((MainCategory) category);
        } else {
            category = Category.getSubCategoryByName(this.name);
            ((SubCategory) category).setMainCategory(Category.getMainCategoryByName(this.mainCategoryName));
        }
        this.connectProducts(category);
    }

    private void connectProducts(Category category) {
        for (String productId : this.productsId) {
            category.addProduct(Product.getProductWithId(productId));
        }
    }

    private void connectSubCategories(MainCategory category) {
        for (String subCategoryName : this.subCategoriesNames) {
            category.addSubCategory(Category.getSubCategoryByName(subCategoryName));
        }
    }

    private void createCategory() {
        Category category;
        if (this.type.equals("main-category")) {
            category = new MainCategory();
        } else
            category = new SubCategory();
        category.setName(this.name);
        category.setSpecialProperties(this.specialProperties);
        category.setId(this.id);
    }

    public void addSubCategory(String subCategoryName) {
        this.subCategoriesNames.add(subCategoryName);
    }

    public void addProduct(String productId) {
        this.productsId.add(productId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialProperties(ArrayList<String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void setProductsId(ArrayList<String> productsId) {
        this.productsId = productsId;
    }

    public void setSubCategoriesNames(ArrayList<String> subCategoriesNames) {
        this.subCategoriesNames = subCategoriesNames;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public void addToDatabase() {
        Database.addCategory(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
