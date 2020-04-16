package model.others;

import model.discount.Discount;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    private static ShoppingCart localShoppingCart;

    private Discount appliedDiscountCode;
    private ArrayList<Product> productsInCart;
    private HashMap<Product, Integer> productsNumberInCarHashMap;
    private HashMap<Product, Seller> productsSellerHashMap;
    public String showProducts(){return null;}
    public boolean isProductInCart(String productId){return true;}
    public void decreaseProductInCart(Product product){}
    public void increaseProductInCart(Product product){}
    public void mergingLocalWithCart(ArrayList<Product> products){}
    public void applyDiscountCode(String code){}
    public double getTotalPrice(){return 1;}
    public void addToCart(Product product, Seller seller){}
    public void buy(){}

    public ShoppingCart() {
    }

    public void setProductsInCart(ArrayList<Product> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public void setProductsNumberInCarHashMap(HashMap<Product, Integer> productsNumberInCarHashMap) {
        this.productsNumberInCarHashMap = productsNumberInCarHashMap;
    }

    public void setProductsSellerHashMap(HashMap<Product, Seller> productsSellerHashMap) {
        this.productsSellerHashMap = productsSellerHashMap;
    }

    public ArrayList<Product> getProductsInCart() {
        return productsInCart;
    }

    public HashMap<Product, Integer> getProductsNumberInCarHashMap() {
        return productsNumberInCarHashMap;
    }

    public HashMap<Product, Seller> getProductsSellerHashMap() {
        return productsSellerHashMap;
    }

    public static void setLocalShoppingCart(ShoppingCart localShoppingCart) {
        ShoppingCart.localShoppingCart = localShoppingCart;
    }

    public void setAppliedDiscountCode(Discount appliedDiscountCode) {
        this.appliedDiscountCode = appliedDiscountCode;
    }


    public static ShoppingCart getLocalShoppingCart() {
        return localShoppingCart;
    }

    public Discount getAppliedDiscountCode() {
        return appliedDiscountCode;
    }


}
