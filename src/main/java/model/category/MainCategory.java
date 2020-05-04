package model.category;

import java.util.ArrayList;

public class MainCategory extends Category {
    private ArrayList<Category> subCategories;


    public MainCategory() {
        super();
        subCategories=new ArrayList<>();
        allMainCategories.add(this);
    }

    public void addSubCategory(SubCategory subCategory){

    }

    @Override
    public String categoryInfoForSending() {
        return null;
    }


    public String getSubcategoriesInfo(String sortField, String sortDirection) {
        return null;
    }

    public boolean isThereSubCategories(String name) {
        return true;
    }

    @Override
    public String toString() {
        return "MainCategory{}";
    }

    public SubCategory getSubCategory(String name) {
        return null;
    }

    public boolean isThereSubCategory(Category subCategory) {
        return true;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }
}
