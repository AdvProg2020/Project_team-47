package model.category;

import model.others.Product;
import java.util.ArrayList;
import java.util.Iterator;

public class SubCategory extends Category {
    private static ArrayList<SubCategory> subCategories;

    public SubCategory(String name) {
        super(name);
    }

    public static boolean doesSubCategoryExist(String name) {
        Iterator<SubCategory> iterator = SubCategory.subCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static void addSubCategory(SubCategory subCategory) {
        SubCategory.subCategories.add(subCategory);
    }

    public static void removeSubCategory(String name) {
        Iterator<SubCategory> iterator = SubCategory.subCategories.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                iterator.remove();
                break;
            }
        }
    }

    public static SubCategory getSubCategory(String name) {
        Iterator<SubCategory> iterator = SubCategory.subCategories.iterator();
        while (iterator.hasNext()) {
            SubCategory subCategory = iterator.next();
            if (subCategory.getName().equalsIgnoreCase(name)) {
                return subCategory;
            }
        }
        return null;
    }
}
