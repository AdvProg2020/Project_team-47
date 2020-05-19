package model.category;


import database.CategoryData;
import model.others.Product;
import model.send.receive.CategoryInfo;

public class SubCategory extends Category {
    private MainCategory mainCategory;


    public SubCategory() {
        super();
        allSubCategories.add(this);
    }

    @Override
    public void updateDatabase() {
        CategoryData categoryData = new CategoryData("sub-category");
        super.updateDatabase(categoryData);
        categoryData.setMainCategoryName(this.mainCategory.getName());
        categoryData.addToDatabase();
    }

    @Override
    public void remove() {
        for (Product product : this.allProducts) {
            Product.removeProduct(product);
        }
        this.removeFromDatabase();
    }

    @Override
    public CategoryInfo categoryInfoForSending() {
        CategoryInfo categoryInfo = new CategoryInfo(this.name, this.specialProperties);
        categoryInfo.setType("sub-category");
        categoryInfo.setMainCategory(this.mainCategory.name);
        for (Product product : allProducts) {
            categoryInfo.addProduct(product);
        }
        return categoryInfo;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }
}
