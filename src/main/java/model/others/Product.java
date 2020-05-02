package model.others;

import com.google.gson.Gson;
import model.category.Category;
import model.discount.Off;
import model.log.Log;
import model.send.receive.ProductInfo;
import model.user.Customer;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Product {
    private static int productIdCounter;
    private static ArrayList<Product> allProducts;
    private int seenTime;
    private String id;
    private String name;
    private String company;
    private String status;
    private HashMap<Seller,Double> price;
    private ArrayList<Seller> sellers;
    private HashMap<User,Integer> numberOfProductSellerHas;
    private Category category;
    private HashMap<String,String> specialProperties;
    private ArrayList<Comment> comments;
    private String description;
    private ArrayList<Score> scores;
    private HashMap<Seller, Off> productOffs;
    private double scoreAverage;
    private Date creatingDate;

    public Product() {
        this.sellers = new ArrayList<>();
        this.numberOfProductSellerHas = new HashMap<>();
        this.price = new HashMap<>();
        this.comments = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.productOffs = new HashMap<>();
        this.id = productIdCreator();
        this.creatingDate = Date.getCurrentDate();
    }

    public static void removeProduct(String id) {
        Product product = getProductWithId(id);
        allProducts.remove(product);
        assert product != null;
        for (Seller seller : product.sellers) {
            seller.removeProduct(product);
        }
        for (Map.Entry<Seller, Off> offEntry : product.productOffs.entrySet()) {
            offEntry.getValue().removeProduct(product);
        }
    }

    private static String productIdCreator() {
        StringBuilder id = new StringBuilder();
        Random randomNumber = new Random();
        String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        id.append(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())));
        id.append(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())));
        id.append(randomNumber.nextInt(10000));
        if (isThereProductWithId(id.toString())) {
            return productIdCreator();
        } else
            return id.toString();
    }

    public static Product getProductWithId(String id) {
        for (Product product : allProducts) {
            if (id.equals(product.id)) {
                return product;
            }
        }
        return null;
    }

    public static boolean isThereProductWithId(String id) {
        for (Product product : allProducts) {
            if (product.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static String getAllProductInfo(String field, String direction) {
        ArrayList<Product> products = Sort.sortProduct(field, direction, allProducts);
        ArrayList<String> productsInfo = new ArrayList<>();
        assert products != null;
        for (Product product : products) {
            productsInfo.add(product.getProductInfo());
        }
        return (new Gson()).toJson(productsInfo);
    }

    public static String getProductById(String id) {
        Product product = getProductWithId(id);
        if (product == null) {
            return "";
        } else {
            return product.getProductInfo();
        }
    }

    public static String getProductsFiltered(String sortField, String direction, ArrayList<Filter> filters) {
        ArrayList<Product> products = Sort.sortProduct(sortField, direction, allProducts);
        ArrayList<String> productsInfo = new ArrayList<>();
        assert products != null;
        for (Product product : products) {
            if (inFilter(product, filters)) {
                productsInfo.add(product.getProductInfo());
            }
        }
        return (new Gson()).toJson(productsInfo);
    }

    private static boolean inFilter(Product product, ArrayList<Filter> filters) {
        for (Filter filter : filters) {
            if (filter.getType().startsWith("equality")) {
                if (!checkEqualityFilter(product, filter))
                    return false;
            } else {
                if (!checkEqualityFilter(product, filter))
                    return false;
            }
        }
        return true;
    }

    private static boolean checkEquationFilter(Product product, Filter filter) {
        if (filter.getFilterKey().contains("price")) {
            for (Map.Entry<Seller, Double> entry : product.getPrice().entrySet()) {
                if (entry.getValue() <= filter.getSecondDouble() && entry.getValue() >= filter.getFirstDouble())
                    return true;
            }
        } else if (filter.getFilterKey().contains("score")) {
            return filter.getFirstDouble() <= product.getScoreAverage() && filter.getSecondDouble() <= product.getScoreAverage();
        } else if (filter.getFilterKey().contains("special-properties")) {
            try {
                int temp = Integer.parseInt(product.getSpecialProperties().get(filter.getFilterKey()));
                return filter.getFirstInt() <= temp && filter.getSecondInt() <= temp;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private static boolean checkEqualityFilter(Product product, Filter filter) {
        if (filter.getType().contains("category")) {
            return product.getCategory().getName().equals(filter.getFilterValue());
        } else if (filter.getType().contains("name")) {
            return product.getName().contains(filter.getFilterValue());
        } else if (filter.getType().contains("special-properties")) {
            try {
                return product.getSpecialProperties().get(filter.getFilterKey()).contains(filter.getFilterValue());
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public String digest() {
        return getProductInfo();
    }

    public double getFinalPrice(Seller seller) {
        if (!this.sellers.contains(seller)) {
            return -1;
        } else if (!this.productOffs.containsKey(seller)) {
            return this.price.get(seller);
        }
        int offPercent = this.productOffs.get(seller).getDiscountPercent();
        if (offPercent < 1) {
            offPercent *= 100;
        }
        return this.price.get(seller) * ((double) ((100 - offPercent) / 100));
    }

    public String getProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategory(this.category.getName());
        productInfo.setId(this.id);
        productInfo.setName(this.name);
        productInfo.setScoreAverage(this.scoreAverage);
        productInfo.setSpecialProperties(specialProperties);
        productInfo.setPriceForSellers(this.getPriceForSeller());
        productInfo.setPricesWithOff(this.getPriceWithOff());
        productInfo.setDescription(this.description);
        productInfo.setNumberOfProductSellerHas(this.numberOfProduct());
        return (new Gson()).toJson(productInfo);
    }

    public static void buy(Log buyLog){}

    private HashMap<String, Integer> numberOfProduct() {
        HashMap<String, Integer> numberOfProduct = new HashMap<>();
        for (Map.Entry<User, Integer> entry : this.numberOfProductSellerHas.entrySet()) {
            numberOfProduct.put(entry.getKey().getUsername(), entry.getValue());
        }
        return numberOfProduct;
    }

    public static void buy(Product product,Seller Seller){}

    private HashMap<String, Double> getPriceWithOff() {
        HashMap<String, Double> price = new HashMap<>();
        for (Seller seller : this.sellers) {
            price.put(seller.getUsername(), getFinalPrice(seller));
        }
        return price;
    }

    private HashMap<String, Double> getPriceForSeller() {
        HashMap<String, Double> price = new HashMap<>();
        for (Map.Entry<Seller, Double> entry : this.price.entrySet()) {
            price.put(entry.getKey().getUsername(), entry.getValue());
        }
        return price;
    }

    public boolean isUserInSellerList(Seller seller) {
        return this.sellers.contains(seller);
    }

    public void addSeller(Seller seller, int numberInStock) {
        this.sellers.add(seller);
        this.numberOfProductSellerHas.put(seller, numberInStock);
    }

    public void addScore(Score score) {
        Score scoredBefore = new Score();
        for (Score repeatedScore : this.scores) {
            if (repeatedScore.getWhoSubmitScore() == score.getWhoSubmitScore()) {
                scoredBefore = repeatedScore;
                break;
            }
        }
        this.scores.remove(scoredBefore);
        this.scores.add(score);
        this.updateScoreAverage();
    }

    private void updateScoreAverage() {
        double score = 0;
        for (Score s : this.scores) {
            score += s.getScore();
        }
        score /= this.scores.size();
        this.scoreAverage = score;
    }

    public String compare(Product secondProduct) {
        ArrayList<String> products = new ArrayList<>();
        products.add(this.getProductInfo());
        products.add(secondProduct.getProductInfo());
        return (new Gson()).toJson(products);
    }

    public String getAllCommentInfo() {
        ArrayList<String> commentInfo = new ArrayList<>();
        for (Comment comment : this.comments) {
            commentInfo.add(comment.getCommentInfoForSending());
        }
        return (new Gson()).toJson(commentInfo);
    }

    public void addComment(String title, String commentText, Customer customer) {
        Comment comment = new Comment();
        comment.setCommentTitle(title);
        comment.setCommentText(commentText);
        comment.setDoesCustomerBought(customer.doesUserBoughtProduct(this));
        comment.setProductCommentBelongTo(this);
        comment.setWhoComment(customer);
        this.comments.add(comment);
    }


    public void changePrice(Seller seller, double price) {
        this.price.remove(seller);
        this.price.put(seller, price);
    }

    public void changeNumberOfProduct(Seller seller, int number) {
        this.numberOfProductSellerHas.remove(seller);
        this.numberOfProductSellerHas.put(seller, number);
    }
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

    public ArrayList<Seller> getSellers() {
        return sellers;
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

    public void setSellers(ArrayList<Seller> sellers) {
        this.sellers = sellers;
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

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public int getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(int seenTime) {
        this.seenTime = seenTime;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public HashMap<Seller, Off> getProductOffs() {
        return productOffs;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }
}
