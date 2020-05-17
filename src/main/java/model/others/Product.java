package model.others;

import controller.Controller;
import database.Database;
import database.ProductData;
import model.category.MainCategory;
import model.category.SubCategory;
import model.discount.Off;
import model.others.request.AddCommentRequest;
import model.others.request.Request;
import model.send.receive.ProductInfo;
import model.user.Customer;
import model.user.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Product {
    private static ArrayList<Product> allProducts;
    private static TreeSet<String> usedId;

    static {
        allProducts = new ArrayList<>();
        usedId = new TreeSet<>();
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
    private ArrayList<ProductSeller> productSellers;

    public Product() {
        this.sellers = new ArrayList<>();
        this.productSellers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.id = productIdCreator();
        usedId.add(this.id);
        Database.updateUsedProductId(usedId);
        allProducts.add(this);
    }

    public static void removeProduct(String id) {
        Product product = getProductWithId(id);
        removeProduct(product);
    }

    public static void removeProduct(Product product) {
        allProducts.remove(product);
        for (Seller seller : product.sellers) {
            seller.removeProduct(product);
        }
        for (ProductSeller productSeller : product.productSellers) {
            if (productSeller.getOff() != null) {
                productSeller.off.removeProduct(product);
            }
        }
        product.removeFromDatabase();
    }

    private static String productIdCreator() {
        String id = Controller.idCreator();
        if (usedId.contains(id)) {
            return productIdCreator();
        } else
            return id;
    }

    public static Product getProductWithId(String id) {
        for (Product product : allProducts) {
            if (id.equalsIgnoreCase(product.id)) {
                return product;
            }
        }
        return null;
    }

    public static boolean isThereProduct(String id) {
        for (Product product : allProducts) {
            if (product.id.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereProduct(Product product) {
        return allProducts.contains(product);
    }

    public static ArrayList<ProductInfo> getAllProductInfo(String field, String direction) {
        ArrayList<Product> products = Sort.sortProduct(field, direction, allProducts);
        ArrayList<ProductInfo> productsInfo = new ArrayList<>();
        assert products != null;
        for (Product product : products) {
            productsInfo.add(product.getProductInfo());
        }
        return productsInfo;
    }

    public static ArrayList<ProductInfo> getProductsFiltered(String sortField, String direction, ArrayList<Filter> filters) {
        ArrayList<Product> products = Sort.sortProduct(sortField, direction, allProducts);
        ArrayList<ProductInfo> productsInfo = new ArrayList<>();
        assert products != null;
        for (Product product : products) {
            if (inFilter(product, filters)) {
                productsInfo.add(product.getProductInfo());
            }
        }
        return productsInfo;
    }

    private static boolean inFilter(Product product, ArrayList<Filter> filters) {
        for (Filter filter : filters) {
            if (filter.getType().toLowerCase().startsWith("equality")) {
                if (!inEqualityFilter(product, filter))
                    return false;
            } else if (filter.getType().toLowerCase().startsWith("equation")) {
                if (!inEquationFilter(product, filter))
                    return false;
            }
        }
        return false;
    }

    private static boolean inEquationFilter(Product product, Filter filter) {
        if (filter.getFilterKey().equalsIgnoreCase("price")) {
            for (ProductSeller productSeller : product.productSellers) {
                if (productSeller.getPrice() <= filter.getSecondDouble() && productSeller.getPrice() >= filter.getFirstDouble())
                    return true;
            }
        } else if (filter.getFilterKey().equalsIgnoreCase("score")) {
            return filter.getFirstDouble() <= product.getScoreAverage() && filter.getSecondDouble() <= product.getScoreAverage();
        } else if (filter.getType().toLowerCase().contains("special-properties")) {
            try {
                double temp = Double.parseDouble(product.getPropertyValue(filter.getFilterKey()));
                return filter.getFirstInt() <= temp && filter.getSecondInt() <= temp;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private static boolean inEqualityFilter(Product product, Filter filter) {
        if (filter.getFilterKey().equalsIgnoreCase("category")) {
            return isInCategoryFilter(product, filter);
        } else if (filter.getFilterKey().equalsIgnoreCase("sub-category")) {
            return isInSubCategoryFilter(product, filter);
        } else if (filter.getFilterKey().equalsIgnoreCase("name")) {
            return isInNameFilter(product, filter);
        } else if (filter.getType().toLowerCase().contains("special-properties")) {
            return isInEqualityPropertyFilter(product, filter);
        } else if (filter.getFilterKey().toLowerCase().equalsIgnoreCase("brand")) {
            return isInBrandFilter(product, filter);
        } else if (filter.getFilterKey().toLowerCase().equalsIgnoreCase("availability")) {
            return isInAvailabilityFilter(product, filter);
        } else if (filter.getFilterKey().toLowerCase().equalsIgnoreCase("seller")) {
            return isInSellerFilter(product, filter);
        }
        return false;
    }

    private static boolean isInSellerFilter(Product product, Filter filter) {
        return product.hasSeller(filter.getFirstFilterValue());
    }

    private static boolean isInNameFilter(Product product, Filter filter) {
        return product.getName().toLowerCase().contains(filter.getFirstFilterValue().toLowerCase());
    }

    private static boolean isInAvailabilityFilter(Product product, Filter filter) {
        if (filter.getFirstFilterValue().equalsIgnoreCase("yes")) {
            return product.isAvailable();
        } else if (filter.getFirstFilterValue().equalsIgnoreCase("no")) {
            return !product.isAvailable();
        }
        return false;
    }

    private static boolean isInBrandFilter(Product product, Filter filter) {
        return product.company.toLowerCase().contains(filter.getFirstFilterValue().toLowerCase());
    }

    private static boolean isInCategoryFilter(Product product, Filter filter) {
        return product.getMainCategory().getName().equalsIgnoreCase(filter.getFirstFilterValue());
    }

    private static boolean isInEqualityPropertyFilter(Product product, Filter filter) {
        try {
            return product.getPropertyValue(filter.getFilterKey()).toLowerCase()
                    .contains(filter.getFirstFilterValue().toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isInSubCategoryFilter(Product product, Filter filter) {
        try {
            return product.getSubCategory().getName().equalsIgnoreCase(filter.getFirstFilterValue());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static void setUsedId(TreeSet<String> usedId) {
        Product.usedId = usedId;
    }

    public void updateDatabase() {
        ProductData productData = new ProductData();
        this.dataObjectSetter(productData);
        this.addSellerToData(productData);
        this.addSellersInfoToData(productData);
        productData.addToDatabase();
    }

    private void addSellersInfoToData(ProductData productData) {
        for (ProductSeller sellerInfo : this.productSellers) {
            productData.addSeller(sellerInfo.seller.getUsername(),
                    sellerInfo.off.getOffId(),
                    sellerInfo.price, sellerInfo.numberInStock);
        }
    }

    private void addSellerToData(ProductData productData) {
        for (Seller seller : this.sellers) {
            productData.addSeller(seller.getUsername());
        }
    }

    private void dataObjectSetter(ProductData productData) {
        productData.setSeenTime(this.seenTime);
        productData.setComments(this.comments);
        productData.setCompany(this.company);
        productData.setDescription(this.description);
        productData.setMainCategory(mainCategory.getName());
        if (subCategory != null)
            productData.setSubCategory(subCategory.getName());
        productData.setId(this.id);
        productData.setScoreAverage(this.scoreAverage);
        productData.setScores(this.scores);
        productData.setSpecialProperties(this.specialProperties);
        productData.setStatus(this.status);
        productData.setName(this.name);
    }

    private boolean hasSeller(String username) {
        for (ProductSeller productSeller : this.productSellers) {
            if (productSeller.getSeller().getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    private boolean isAvailable() {
        for (ProductSeller productSeller : this.productSellers) {
            if (productSeller.getNumberInStock() > 0)
                return true;
        }
        return false;
    }

    private String getPropertyValue(String key) throws Exception {
        for (Map.Entry<String, String> entry : this.specialProperties.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(key)) {
                return entry.getValue();
            }
        }
        throw new Exception();
    }

    public void removeSellerFromProduct(Seller seller) {
        if (productSellers.size() == 1) {
            removeProduct(this);
            return;
        }
        this.sellers.remove(seller);
        for (ProductSeller productSeller : this.productSellers) {
            if (seller == productSeller.getSeller()) {
                productSellers.remove(productSeller);
                break;
            }
        }
        seller.removeProduct(this);
        for (ProductSeller productSeller : this.productSellers) {
            if (productSeller.getOff() != null) {
                productSeller.getOff().removeProduct(this);
            }
        }
        this.updateDatabase();
    }

    public ProductInfo digest() {
        return getProductInfo();
    }

    public ProductInfo getProductInfo() {
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
        return productInfo;
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
        for (Score repeatedScore : this.scores) {
            if (repeatedScore.getWhoSubmitScore().equals(score.getWhoSubmitScore())) {
                this.scores.remove(repeatedScore);
                break;
            }
        }
        this.scores.add(score);
        this.updateScoreAverage();
    }

    private void updateScoreAverage() {
        if (this.scores.size() == 0) {
            return;
        }
        double score = 0;
        for (Score s : this.scores) {
            score += s.getScore();
        }
        score /= this.scores.size();
        this.scoreAverage = score;
    }

    public ArrayList<ProductInfo> compare(Product secondProduct) {
        ArrayList<ProductInfo> products = new ArrayList<>();
        products.add(this.getProductInfo());
        products.add(secondProduct.getProductInfo());
        return products;
    }

    public ArrayList<Comment> getAllCommentInfo() {
        return this.comments;
    }

    public void addComment(String title, String commentText, Customer customer) {
        Comment comment = new Comment();
        comment.setCommentTitle(title);
        comment.setCommentText(commentText);
        comment.setDoesCustomerBought(customer.doesUserBoughtProduct(this));
        comment.setProductId(this.id);
        comment.setProductName(this.name);
        comment.setWhoComment(customer.getUsername());
        addCommentRequest(comment);
    }

    private void addCommentRequest(Comment comment) {
        Request request = new Request();
        request.setRequestSender(comment.getWhoComment());
        request.setType("add-comment");
        request.setMainRequest(new AddCommentRequest(comment));
        request.addToDatabase();
    }

    public void changePrice(Seller seller, double price) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == seller) {
                productSeller.setPrice(price);
                return;
            }
        }
    }

    public void changeNumberOfProduct(Seller seller, int number) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == seller) {
                productSeller.setNumberInStock(number);
                return;
            }
        }
    }

    public double getMinimumPrice() {
        double min = Double.MAX_VALUE;
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getPrice() < min) {
                min = productSeller.getPrice();
            }
        }
        return min;
    }

    public void addSeenTime() {
        this.seenTime++;
        this.updateDatabase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public HashMap<String, String> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(HashMap<String, String> specialProperties) {
        this.specialProperties = specialProperties;
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
    }

    public int getNumberInStock(Seller seller) {
        return getProductSeller(seller).getNumberInStock();
    }

    public void addOff(Off off, Seller seller) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller() == seller) {
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
        ProductSeller productSeller = getProductSeller(seller);
        if (productSeller != null) {
            return productSeller.getPriceWithOff();
        } else
            return -1;
    }

    public void addSellerFromDatabase(Seller seller, Off off, double price, int numberInStock) {
        this.sellers.add(seller);
        ProductSeller productSeller = new ProductSeller();
        productSeller.setPrice(price);
        productSeller.setSeller(seller);
        productSeller.setNumberInStock(numberInStock);
        productSeller.setOff(off);
        this.productSellers.add(productSeller);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public ProductInfo attributes() {
        return this.getProductInfo();
    }

    public void removeFromDatabase() {
        Database.removeProduct(this.id);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
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

            int offPercent = off.getPercent();
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
    }//end ProductSeller class

}//end Product class
