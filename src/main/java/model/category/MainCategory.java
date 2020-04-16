package model.category;

import java.util.ArrayList;

public class MainCategory extends Category {


    public MainCategory() {
    }

    @Override
    public String categoryInfoForSending() {
        return null;
    }



    @Override
    public String toString() {
        return "MainCategory{}";
    }
    public Category getSubCategory(String name){return null;}
    public boolean isThereSubCategory(Category subCategory){return true;}
    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }
}
