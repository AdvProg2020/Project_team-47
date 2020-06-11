package model.others.request;

import model.category.Category;
import model.category.SubCategory;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.others.Product;
import model.others.SpecialProperty;
import model.send.receive.RequestInfo;
import model.user.Seller;
import model.user.User;

public class EditProductRequest extends MainRequest {
    private String field;
    private String newValue;
    private SpecialProperty property;
    private String productId;
    private String sellerUsername;
    private String changeType;

    public EditProductRequest(String field, String newValue, String changeType, SpecialProperty property) {
        this.field = field;
        this.newValue = newValue;
        this.changeType = changeType;
        this.property = property;
    }

    @Override
    public void requestInfoSetter(RequestInfo requestInfo) {
        requestInfo.setEditInfo(field, newValue, changeType, productId, property);
    }

    @Override
    void accept() {
        Product product = null;
        try {
            product = Product.getProductWithId(productId);
        } catch (ProductDoesntExistException e) {
            e.printStackTrace();
            return;
        }
        Seller seller = null;
        try {
            seller = (Seller) Seller.getUserByUsername(sellerUsername);
        } catch (UserNotExistException e) {
            e.printStackTrace();
            return;
        }
        switch (field) {
            case "name" -> product.setName(newValue);
            case "price" -> product.changePrice(seller, Double.parseDouble(newValue));
            case "number-of-product" -> product.changeNumberOfProduct(seller, Integer.parseInt(newValue));
            case "category" -> {
                try {
                    product.setMainCategory(Category.getMainCategoryByName(newValue));
                    product.setSubCategory(null);
                } catch (CategoryDoesntExistException e) {
                    e.printStackTrace();
                }
            }
            case "sub-category" -> {
                try {
                    SubCategory subCategory = Category.getSubCategoryByName(newValue);
                    product.setSubCategory(subCategory);
                    product.setMainCategory(subCategory.getMainCategory());
                } catch (CategoryDoesntExistException e) {
                    e.printStackTrace();
                }
            }
            case "special-properties" -> changeSpecialProperties(product);
            case "description" -> product.setDescription(newValue);
        }
        product.setStatus("EDIT_ACCEPTED");
        product.updateDatabase();
    }

    @Override
    boolean update() {
        Seller seller;
        User user = null;
        try {
            user = Seller.getUserByUsername(sellerUsername);
        } catch (UserNotExistException ignored) {
        }
        if (!(user instanceof Seller)) {
            return false;
        } else {
            seller = (Seller) user;
        }
        Product product = null;
        try {
            product = Product.getProductWithId(productId);
        } catch (ProductDoesntExistException e) {
            return false;
        }

        if (!seller.hasProduct(product) || !Seller.isThereSeller(seller)) {
            return false;
        }

        return switch (field) {
            case "name", "price", "number-of-product", "description" -> true;
            case "category" -> updateForCategory("main-category", product);
            case "sub-category" -> updateForCategory("sub-category", product);
            case "specialProperties" -> updateProperties();
            default -> false;
        };
    }

    @Override
    public void decline() {
        Product product = null;
        try {
            product = Product.getProductWithId(this.productId);
        } catch (ProductDoesntExistException e) {
            return;
        }
        product.setStatus("EDIT_DECLINED");
        product.updateDatabase();
    }

    private boolean updateProperties() {
        Product product = null;
        try {
            product = Product.getProductWithId(productId);
        } catch (ProductDoesntExistException e) {
            return false;
        }
        Category category = product.getSubCategory();
        if (category == null) {
            category = product.getMainCategory();
        }
        return true;
    }

    private boolean updateForCategory(String categoryType, Product product) {
        Category category = null;
        if (categoryType.equals("main-category")) {
            try {
                category = Category.getMainCategoryByName(newValue);
            } catch (CategoryDoesntExistException e) {
                return false;
            }

        } else if (categoryType.equals("sub-category")) {
            try {
                category = Category.getSubCategoryByName(newValue);
            } catch (CategoryDoesntExistException e) {
                return false;
            }
        }
        assert category != null;
        for (SpecialProperty property : category.getSpecialProperties()) {
            if (!product.getSpecialProperties().contains(property))
                product.getSpecialProperties().add(property);
        }

        return true;
    }

    private void changeSpecialProperties(Product product) {
        switch (changeType) {
            case "remove" -> product.getSpecialProperties().remove(property);
            case "add" -> {
                if (product.getSpecialProperties().contains(property)) {
                    SpecialProperty temp = product.getSpecialProperties().get(product.getSpecialProperties().indexOf(property));
                    temp.setNumericValue(property.getNumericValue());
                    temp.setValue(property.getValue());
                } else {
                    product.getSpecialProperties().add(property);
                }
            }
        }
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSeller(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
}
