package model.discount;

import model.others.Date;
import model.others.Product;
import model.user.Seller;

import java.util.ArrayList;

public class Off extends Discount {
    private static ArrayList<Off> allOffs;
    private String offId;
    private String offStatus;
    private ArrayList<Product> products;
    private Seller seller;


    public Off(Date discountStartTime, Date discountFinishTime,
               int discountPercent, String offId, String offStatus,
               ArrayList<Product> products, Seller seller) {
        super(discountStartTime, discountFinishTime, discountPercent);
        this.offId = offId;
        this.offStatus = offStatus;
        this.products = products;
        this.seller = seller;
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public static void setAllOffs(ArrayList<Off> allOffs) {
        Off.allOffs = allOffs;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public static boolean isThereOff(String offId){
        return allOffs.stream().anyMatch(off -> offId.equals(off.offId));
    }
    public static Off getOffById(String offId){
        return allOffs.stream().filter(off -> offId.equals
                (off.offId)).findAny().orElse(null);
    }
    @Override
    public String toString() {return null;}//





    public void addProduct(Product product){
        products.add(product);
    }
    public void removeProduct(Product product){
        products.remove(product);
    }


    public boolean isItInOff(Product product){
        return products.contains(product);
    }

    public static String getAllProductsInOffsInfo(String order,ArrayList<String> filter){return null;}//

    public static boolean isProductInAnyOff(Product product){
        return allOffs.stream().anyMatch(off -> off.isItInOff(product));
    }


    public String getOffId() {
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

    public void removeProductFromOff(Product product){
        products.remove(product);
    }

    @Override
    public String discountInfoForSending() {
        return null;
    }//
}