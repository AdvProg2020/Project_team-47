package database;

import model.category.Category;
import model.discount.Off;
import model.ecxeption.common.OffDoesntExistException;
import model.ecxeption.product.CategoryDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.others.Comment;
import model.others.Product;
import model.others.Score;
import model.others.SpecialProperty;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;

public class ProductData {
    private final ArrayList<String> sellersUsernames;
    private final ArrayList<ProductSeller> productSellers;
    private int seenTime;
    private String id;
    private String company;
    private String status;
    private String mainCategory;
    private String subCategory;
    private String description;
    private String name;
    private double scoreAverage;
    private ArrayList<Score> scores;
    private ArrayList<Comment> comments;
    private ArrayList<SpecialProperty> specialProperties;
    private String fileExtension;
    private byte[] file;
    private String filePath;

    public ProductData() {
        this.sellersUsernames = new ArrayList<>();
        this.productSellers = new ArrayList<>();
    }

    static void addProducts(ArrayList<ProductData> products) {
        if (products == null)
            return;
        for (ProductData product : products) {
            product.createProduct();
        }
    }

    static void connectRelations(ArrayList<ProductData> products) {
        for (ProductData product : products) {
            product.connectRelations();
        }
    }

    private void connectRelations() {
        try {
            Product product = Product.getProductWithId(this.id);
            product.setMainCategory(Category.getMainCategoryByName(this.mainCategory));
            if (this.subCategory != null)
                product.setSubCategory(Category.getSubCategoryByName(this.subCategory));
            this.connectSellers(product);
            this.connectSellers(product);
        } catch (ProductDoesntExistException | CategoryDoesntExistException e) {
            e.printStackTrace();
        }
    }

    private void connectSellers(Product product) {
        try {
            for (ProductSeller productSeller : productSellers) {
                Off off = null;
                try {
                    off = Off.getOffById(productSeller.offId);
                } catch (OffDoesntExistException ignored) {
                }
                Seller seller = (Seller) User.getUserByUsername(productSeller.sellerUsername);
                product.addSellerFromDatabase(seller, off, productSeller.price, productSeller.numberInStock);
            }
        } catch (UserNotExistException e) {
            e.printStackTrace();
        }
    }

    private void createProduct() {
        Product product = new Product();
        product.setCompany(this.company);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setSpecialProperties(this.specialProperties);
        product.setSeenTime(this.seenTime);
        product.setId(this.id);
        product.setComments(this.comments);
        product.setScoreAverage(this.scoreAverage);
        product.setScores(this.scores);
        product.setStatus(this.status);
        product.setFileExtension(this.fileExtension);
        product.setFile(this.file);
        product.setFilePath(this.filePath);
    }

    public void addToDatabase() {
        Database.addProduct(this, this.id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSeller(String sellerUsername) {
        this.sellersUsernames.add(sellerUsername);
    }

    public void addSeller(String sellerUsername, String offId, double price, int numberInStock) {
        ProductSeller productSeller = new ProductSeller();
        productSeller.numberInStock = numberInStock;
        productSeller.offId = offId;
        productSeller.sellerUsername = sellerUsername;
        productSeller.price = price;
        this.productSellers.add(productSeller);
    }

    public void setSeenTime(int seenTime) {
        this.seenTime = seenTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setSpecialProperties(ArrayList<SpecialProperty> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public void setScoreAverage(double scoreAverage) {
        this.scoreAverage = scoreAverage;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private static class ProductSeller {
        private String offId;
        private String sellerUsername;
        private double price;
        private int numberInStock;
    }

}

