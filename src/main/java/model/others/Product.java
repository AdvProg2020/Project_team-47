package model.others;

import model.category.Category;
import model.discount.Off;
import model.log.Log;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private static int productIdCounter;
    private static ArrayList<Product> allProducts;
    private int seenTime;
    private int id;
    private String name;
    private String company;
    private String status;
    private HashMap<Seller,Double> price;
    private ArrayList<User> seller;
    private HashMap<User,Integer> numberOfProductSellerHas;
    private Category category;
    private HashMap<String,String> specialProperties;
    private ArrayList<Comment> comments;
    private String description;
    private ArrayList<Score> scores;
    private HashMap<Seller, Off> productOffs;
    private double scoreAverage;

    public static void removeProduct(String id){}

    public static Product getProductWithId(String id){return null;}

    public String getProductInfo(){return null;}

    public static String getAllProductInfo(String field,String direction){return null;}

    public static String getProductById(String id){return null;}

    public boolean isUserInSellerList(User seller){return true;}

    public static void buy(Log buyLog){}

    public void addSeller(Seller seller, int numberInStock){}


    public static void buy(Product product,Seller Seller){}

    public void addScore(Score score){}
    public static String getProductsFiltered(String sortField,String direction,HashMap<String,String> filters){return null;}

    public String attributes(){return null;}
    public String compare(Product secondProduct){return null;}
    public String getAllCommentInfo(){return null;}
    public void addComment(String title,String commentText){}

    public static int getProductIdCounter() {
        return productIdCounter;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getStatus() {
        return status;
    }

    public HashMap<Seller, Double> getPrice() {
        return price;
    }

    public ArrayList<User> getSeller() {
        return seller;
    }

    public HashMap<User, Integer> getNumberOfProductSellerHas() {
        return numberOfProductSellerHas;
    }

    public Category getCategory() {
        return category;
    }

    public HashMap<String, String> getSpecialProperties() {
        return specialProperties;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getDescription() {
        return description;
    }

    public double getScoreAverage() {
        return scoreAverage;
    }


    public static void setProductIdCounter(int productIdCounter) {
        Product.productIdCounter = productIdCounter;
    }

    public static void setAllProducts(ArrayList<Product> allProducts) {
        Product.allProducts = allProducts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(HashMap<Seller, Double> price) {
        this.price = price;
    }

    public void setSeller(ArrayList<User> seller) {
        this.seller = seller;
    }

    public void setNumberOfProductSellerHas(HashMap<User, Integer> numberOfProductSellerHas) {
        this.numberOfProductSellerHas = numberOfProductSellerHas;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSpecialProperties(HashMap<String, String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScoreAverage(double scoreAverage) {
        this.scoreAverage = scoreAverage;
    }

    public void setProductOffs(HashMap<Seller, Off> productOffs) {
        this.productOffs = productOffs;
    }

    public int getId() {
        return id;
    }

    public Product() {
    }
}
