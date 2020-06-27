package database;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.others.SpecialProperty;

import java.util.ArrayList;

public class CategoryData {
    private final String type;
    private String name;
    private ArrayList<SpecialProperty> specialProperties;
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
        if (categories == null)
            return;
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
        try {
            Category category;
            if (this.type.equals("main-category")) {
                category = Category.getMainCategoryByName(this.name);
                this.connectSubCategories((MainCategory) category);
            } else {
                category = Category.getSubCategoryByName(this.name);
                ((SubCategory) category).setMainCategory(Category.getMainCategoryByName(this.mainCategoryName));
            }
            this.connectProducts(category);
        } catch (CategoryDoesntExistException e) {
            e.printStackTrace();
        }
    }

    private void connectProducts(Category category) {
        for (String productId : this.productsId) {
            try {
                category.addProduct(Product.getProductWithId(productId));
            } catch (ProductDoesntExistException ignored) {
            }
        }
    }

    private void connectSubCategories(MainCategory category) {
        for (String subCategoryName : this.subCategoriesNames) {
            try {
                category.addSubCategory(Category.getSubCategoryByName(subCategoryName));
            } catch (CategoryDoesntExistException e) {
                e.printStackTrace();
            }
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

    public void setSpecialProperties(ArrayList<SpecialProperty> specialProperties) {
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
