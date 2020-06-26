package model.category;

import database.CategoryData;
import model.others.Product;
import model.others.Sort;
import model.send.receive.CategoryInfo;

import java.util.ArrayList;

public class MainCategory extends Category {
    private final ArrayList<SubCategory> subCategories;


    public MainCategory() {
        super();
        subCategories = new ArrayList<>();
        allMainCategories.add(this);
    }

    @Override
    public void updateDatabase() {
        CategoryData categoryData = new CategoryData("main-category");
        super.updateDatabase(categoryData);
        for (Category subCategory : this.subCategories) {
            categoryData.addSubCategory(subCategory.getName());
        }
        categoryData.addToDatabase();
    }

    @Override
    public void remove() {
        for (Product product : this.allProducts) {
            Product.removeProduct(product);
        }
        for (SubCategory subCategory : subCategories) {
            subCategory.removeFromDatabase();
        }
        Category.allSubCategories.removeAll(this.subCategories);
        this.removeFromDatabase();
    }

    public void addSubCategory(SubCategory subCategory) {
        subCategories.add(subCategory);
        this.updateDatabase();
    }

    @Override
    public CategoryInfo categoryInfoForSending() {
        CategoryInfo categoryInfo = new CategoryInfo(this.name, this.specialProperties);
        categoryInfo.setType("main-category");
        for (Product product : this.allProducts) {
            categoryInfo.addProduct(product);
        }
        ArrayList<String> subCategories = new ArrayList<>();
        for (SubCategory subCategory : this.subCategories) {
            subCategories.add(subCategory.name);
        }
        categoryInfo.setSubCategories(subCategories);
        return categoryInfo;
    }


    public ArrayList<CategoryInfo> getSubcategoriesInfo(String sortField, String sortDirection) {
        ArrayList<CategoryInfo> subcategoriesInfo = new ArrayList<>();
        ArrayList<SubCategory> subCategories = Sort.sortSubCategories(sortField, sortDirection, this.subCategories);
        for (SubCategory subCategory : subCategories) {
            subcategoriesInfo.add(subCategory.categoryInfoForSending());
        }
        return subcategoriesInfo;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }
}
