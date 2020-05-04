package model.others;

import com.google.gson.Gson;
import controller.Controller;
import model.category.MainCategory;
import model.category.SubCategory;
import model.discount.Off;
import model.send.receive.ProductInfo;
import model.user.Customer;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Product {
    private static int productIdCounter;
    private static ArrayList<Product> allProducts;

    static {
        allProducts = new ArrayList<>();
    }

    private int seenTime;
    private String id;
    private String name;
    private String company;
    private String status;
    private ArrayList<Seller> sellers;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private HashMap<String, String> specialProperties;
    private ArrayList<Comment> comments;
    private String description;
    private ArrayList<Score> scores;
    private double scoreAverage;
    private Date creatingDate;
    private ArrayList<ProductSeller> productSellers;

    public Product() {
        this.sellers = new ArrayList<>();
        this.productSellers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.creatingDate = Date.getCurrentDate();
        this.id = productIdCreator();
        allProducts.add(this);
    }

    public static void removeProduct(String id) {
        Product product = getProductWithId(id);
        removeProduct(product);
    }

    private static void removeProduct(Product product) {
        allProducts.remove(product);
        assert product != null;
    }

    private static String productIdCreator() {
        String id = Controller.idCreator();
        if (isThereProduct(id)) {
            return productIdCreator();
        } else
            return id;
    }

    public static Product getProductWithId(String id) {
        for (Product product : allProducts) {
            if (id.equals(product.id)) {
                return product;
            }
        }
        return null;
    }

    public static boolean isThereProduct(String id) {
        for (Product product : allProducts) {
            if (product.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereProduct(Product product) {
        return allProducts.contains(product);
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
            } else if (filter.getType().startsWith("equation")) {
                if (!checkEquationFilter(product, filter))
                    return false;
            }
        }
        return true;
    }

    private static boolean checkEquationFilter(Product product, Filter filter) {
        if (filter.getFilterKey().contains("price")) {
            for (ProductSeller productSeller : product.productSellers) {
                if (productSeller.getPrice() <= filter.getSecondDouble() && productSeller.getPrice() >= filter.getFirstDouble())
                    return true;
            }
        } else if (filter.getFilterKey().contains("score")) {
            return filter.getFirstDouble() <= product.getScoreAverage() && filter.getSecondDouble() <= product.getScoreAverage();
        } else if (filter.getType().contains("special-properties")) {
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
        if (filter.getFilterKey().contains("category")) {
            return product.getMainCategory().getName().equals(filter.getFirstFilterValue());
        } else if (filter.getFilterKey().contains("sub-category")) {
            try {
                return product.getSubCategory().getName().equals(filter.getFirstFilterValue());
            } catch (NullPointerException e) {
                return false;
            }
        } else if (filter.getFilterKey().contains("name")) {
            return product.getName().contains(filter.getFirstFilterValue());
        } else if (filter.getType().contains("special-properties")) {
            try {
                return product.getSpecialProperties().get(filter.getFilterKey()).contains(filter.getFirstFilterValue());
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void buy(Product product, Seller Seller) {
    }

    public static int getProductIdCounter() {
        return productIdCounter;
    }

    public static void setProductIdCounter(int productIdCounter) {
        Product.productIdCounter = productIdCounter;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static void setAllProducts(ArrayList<Product> allProducts) {
        Product.allProducts = allProducts;
    }

    public void removeProduct(Seller seller) {
        if (productSellers.size() == 1) {
            removeProduct(this);
            return;
        }
        seller.removeProduct(this);
        for (ProductSeller productSeller : this.productSellers) {
            if (productSeller.getOff() != null) {
                productSeller.getOff().removeProduct(this);
            }
        }
    }

    public String digest() {
        return getProductInfo();
    }

    public String getProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setMainCategory(this.mainCategory.getName());
        if (this.subCategory != null) {
            productInfo.setSubCategory(this.subCategory.getName());
        }
        productInfo.setId(this.id);
        productInfo.setName(this.name);
        productInfo.setScoreAverage(this.scoreAverage);
        productInfo.setSpecialProperties(specialProperties);
        productInfo.setDescription(this.description);
        for (ProductSeller productSeller : productSellers) {
            productInfo.addProductSeller(productSeller.getSeller().getUsername(), productSeller.getPrice(),
                    productSeller.getPriceWithOff(), productSeller.getNumberInStock());
        }
        return (new Gson()).toJson(productInfo);
    }

    public boolean isUserInSellerList(Seller seller) {
        return this.sellers.contains(seller);
    }

    public void addSeller(Seller seller, int numberInStock, double price) {
        this.sellers.add(seller);
        ProductSeller productSeller = new ProductSeller();
        productSeller.setPrice(price);
        productSeller.setSeller(seller);
        productSeller.setNumberInStock(numberInStock);
        this.productSellers.add(productSeller);
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

    public void removeSeller(Seller seller) {
        ArrayList<ProductSeller> found = new ArrayList<>();
        for (ProductSeller productSeller : this.productSellers) {
            if (seller == productSeller.getSeller()) {
                found.add(productSeller);
            }
        }
        this.productSellers.removeAll(found);
    }

    public void changePrice(Seller seller, double price) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == seller) {
                productSeller.setPrice(price);
            }
        }
    }

    public void changeNumberOfProduct(Seller seller, int number) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == seller) {
                productSeller.setNumberInStock(number);
            }
        }
    }

    public void addSeenTime() {
        this.seenTime++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(ArrayList<Seller> sellers) {
        this.sellers = sellers;
    }

    public HashMap<String, String> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(HashMap<String, String> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScoreAverage() {
        return scoreAverage;
    }

    public void setScoreAverage(double scoreAverage) {
        this.scoreAverage = scoreAverage;
    }

    public String getId() {
        return id;
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

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    public ArrayList<ProductSeller> getProductSellers() {
        return productSellers;
    }

    public void setProductSellers(ArrayList<ProductSeller> productSellers) {
        this.productSellers = productSellers;
    }

    public ProductSeller getProductSeller(Seller seller) {
        for (ProductSeller productSeller : this.productSellers) {
            if (seller == productSeller.seller) {
                return productSeller;
            }
        }
        return null;
    }

    public void decreaseProduct(Seller seller, int productNumber) {
        ProductSeller productSeller = getProductSeller(seller);
        productSeller.decrease(productNumber);
        if (productSeller.getNumberInStock() <= 0) {
            this.removeProduct(seller);
        }
    }

    public int getNumberInStock(Seller seller) {
        return getProductSeller(seller).getNumberInStock();
    }

    public void addOff(Off off) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == off.getSeller()) {
                productSeller.setOff(off);
                return;
            }
        }
    }

    public void removeOff(Off off) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == off.getSeller() && productSeller.getOff() == off) {
                productSeller.setOff(null);
                return;
            }
        }
    }

    public double getFinalPrice(Seller seller) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == seller) {
                return productSeller.getPriceWithOff();
            }
        }
        return -1;
    }

    public String attributes() {
        return this.getProductInfo();
    }

    static class ProductSeller {
        private Off off;
        private Seller seller;
        private double price;
        private int numberInStock;


        public double getPriceWithOff() {
            if (off == null)
                return price;
            else if (off.isOffFinished() || !off.isOffStarted())
                return price;

            int offPercent = off.getDiscountPercent();
            if (offPercent < 1) {
                offPercent *= 100;
            }
            return price * ((double) ((100 - offPercent) / 100));
        }

        public void decrease(int number) {
            numberInStock -= number;
        }

        public Off getOff() {
            return off;
        }

        public void setOff(Off off) {
            this.off = off;
        }

        public Seller getSeller() {
            return seller;
        }

        public void setSeller(Seller seller) {
            this.seller = seller;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getNumberInStock() {
            return numberInStock;
        }

        public void setNumberInStock(int numberInStock) {
            this.numberInStock = numberInStock;
        }
    }
}
