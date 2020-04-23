package model.discount;

import model.others.Date;
import model.others.Product;
import model.user.Seller;

import java.util.ArrayList;

public class Off extends Discount {
    private static ArrayList<Off> allOffs;
    private int offId;
    private String offStatus;
    private ArrayList<Product> products;
    private Seller seller;


    public Off(Date discountStartTime, Date discountFinishTime,
               int discountPercent, int offId, String offStatus,
               ArrayList<Product> products, Seller seller) {
        super(discountStartTime, discountFinishTime, discountPercent);
        this.offId = offId;
        this.offStatus = offStatus;
        this.products = products;
        this.seller = seller;
    }

    public static boolean isThereOff(String offId){return true;}
    public static Off getOffById(String offId){return null;}
    @Override
    public String toString() {return null;}





    public void addProduct(Product product){}
    public void removeProduct(Product product){}


    public boolean isItInOff(Product product){return true;}

    public static String getAllProductsInOffsInfo(String order,ArrayList<String> filter){return null;}

    public static boolean isProductInAnyOff(Product product){return true;}







    public int getOffId() {
        return offId;
    }

    public String getOffStatus() {
        return offStatus;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Seller getSeller() {
        return seller;
    }



    public void setOffStatus(String offStatus) {
        this.offStatus = offStatus;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public String discountInfoForSending() {
        return null;
    }
}
