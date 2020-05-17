package model.category;

import model.send.receive.CategoryInfo;

import java.util.ArrayList;

public class MainCategory extends Category {
    private ArrayList<Category> subCategories;


    public MainCategory() {
        super();
        subCategories=new ArrayList<>();
        allMainCategories.add(this);
    }

    public void addSubCategory(SubCategory subCategory){
        subCategories.add(subCategory);
    }

    @Override
    public CategoryInfo categoryInfoForSending() {
        return null;
    }


    public CategoryInfo getSubcategoriesInfo(String sortField, String sortDirection) {
        return null;
    }

    public boolean isThereSubCategories(String name) {
        Iterator<SubCategory> iterator = Category.subCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "MainCategory{}";
    }

    public SubCategory getSubCategory(String name) {
        Iterator<SubCategory> iterator = SubCategory.subCategories.iterator();
        while (iterator.hasNext()) {
            SubCategory subCategory = iterator.next();
            if (subCategory.getName().equalsIgnoreCase(name)) {
                return subCategory;
            }
        }
        return null;
    }

    public boolean isThereSubCategory(Category subCategory) {
        Iterator<SubCategory> iterator = SubCategory.subCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }
}
