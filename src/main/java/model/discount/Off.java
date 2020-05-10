package model.discount;

import model.others.Date;
import model.others.Filter;
import model.others.Product;
import model.others.Sort;
import model.user.Seller;

import java.util.ArrayList;

public class Off extends Discount {
    private static ArrayList<Off> allOffs;
    private String offId;
    private String offStatus;
    private ArrayList<Product> products;
    private Seller seller;

    static {
        allOffs = new ArrayList<>();
    }

    public Off() {
        super();
        products = new ArrayList<>();
        allOffs.add(this);
        this.offId = offIdCreator();
    }

    private String offIdCreator() {
        return null;
    }//

    public static String getAllOffsInfo() {
        return null;
    }//

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public static void setAllOffs(ArrayList<Off> allOffs) {
        Off.allOffs = allOffs;
    }

    public static boolean isThereOff(String offId) {
        return allOffs.stream().anyMatch(off -> offId.equals(off.offId));
    }

    public static Off getOffById(String offId) {
        return allOffs.stream().filter(off -> offId.equals
                (off.offId)).findAny().orElse(null);
    }

    public static String getAllProductsInOffsInfo(String sortField, String direction, ArrayList<Filter> filter) {
        //ArrayList<Product> products = Sort.sortProduct(sortField, direction, filter)
        return null;
    }//

    public static boolean isProductInAnyOff(Product product) {
        return allOffs.stream().anyMatch(off -> off.isItInOff(product));
    }

    public boolean isOffStarted() {
        return discountStartTime.compareTo(Date.getCurrentDate()) >= 0;
    }

    public boolean isOffFinished() {
        return discountFinishTime.compareTo(Date.getCurrentDate()) <= 0;
    }

    @Override
    public String toString() {
        return null;
    }//

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public boolean isItInOff(Product product) {
        return products.contains(product);
    }

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public String getOffStatus() {
        return offStatus;
    }

    public void setOffStatus(String offStatus) {
        this.offStatus = offStatus;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void removeProductFromOff(Product product) {
        products.remove(product);
    }

    @Override
    public String discountInfoForSending() {
        return null;
    }//
}