package model.category;


import model.send.receive.CategoryInfo;

public class SubCategory extends Category {
    private MainCategory mainCategory;


    public SubCategory() {
        super();
        allSubCategories.add(this);
    }

    @Override
    public CategoryInfo categoryInfoForSending() {
        return null;
    }

    @Override
    public String toString() {
        return "SubCategory{}";
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }
}
