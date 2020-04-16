package model.category;


public class SubCategory extends Category {
    private Category mainCategory;


    public SubCategory() {
    }

    @Override
    public String categoryInfoForSending() {
        return null;
    }



    @Override
    public String toString() {
        return "SubCategory{}";
    }
}
