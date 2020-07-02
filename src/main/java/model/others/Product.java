package model.others;

import controller.Controller;
import database.Database;
import database.ProductData;
import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.discount.Off;
import model.ecxeption.Exception;
import model.ecxeption.common.DateException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.request.AddCommentRequest;
import model.others.request.Request;
import model.send.receive.ProductInfo;
import model.user.Customer;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

public class Product {
    private static final ArrayList<Product> allProducts;
    private static TreeSet<String> usedId;

    static {
        allProducts = new ArrayList<>();
        usedId = new TreeSet<>();
    }

    private final ArrayList<Seller> sellers;
    private final TreeSet<ProductSeller> productSellers;
    private int seenTime;
    private String id;
    private String name;
    private String company;
    private String status;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private ArrayList<SpecialProperty> specialProperties;
    private ArrayList<Comment> comments;
    private String description;
    private ArrayList<Score> scores;
    private byte[] file;
    private String fileExtension;
    private double scoreAverage;

    public Product() {
        this.sellers = new ArrayList<>();
        this.productSellers = new TreeSet<>();
        this.comments = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.id = productIdCreator();
        usedId.add(this.id);
        Database.updateUsedProductId(usedId);
        allProducts.add(this);
    }

    public static void removeProduct(String id) {
        try {
            Product product = getProductWithId(id);
            product.removeProduct();
        } catch (ProductDoesntExistException e) {
            e.printStackTrace();
        }
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

    public static Product getProductWithId(String id) throws ProductDoesntExistException {
        for (Product product : allProducts) {
            if (id.equalsIgnoreCase(product.id)) {
                return product;
            }
        }
        throw new ProductDoesntExistException();
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
        ArrayList<ProductSeller> productSellers = new ArrayList<>(product.productSellers);
        for (Filter filter : filters) {
            if (filter.getType().equals("off")) {
                if (!product.inOffFilter(productSellers, filter))
                    return false;
            } else {
                if (!inFilter(product, filter))
                    return false;
            }
        }
        return true;
    }

    private static boolean inFilter(Product product, Filter filter) {
        return switch (filter.getFilterKey()) {
            case "price" -> product.inPriceFilter(filter);
            case "score" -> product.inScoreFilter(filter);
            case "brand" -> product.inBrandFilter(filter);
            case "name" -> product.inNameFilter(filter);
            case "seller" -> product.inSellerFilter(filter);
            case "category" -> product.inCategoryFilter(filter);
            case "sub-category" -> product.inSubcategoryFilter(filter);
            case "availability" -> product.inAvailabilityFilter(filter);
            default -> product.inPropertiesFilter(filter);
        };
    }

    public static void setUsedId(TreeSet<String> usedId) {
        if (usedId == null)
            return;
        Product.usedId = usedId;
    }

    private boolean inOffFilter(ArrayList<ProductSeller> productSellers, Filter filter) {
        return switch (filter.getFilterKey()) {
            case "be in off" -> inOff();
            case "not finished off" -> inNotFinishedOff(productSellers);
            case "be in special off" -> inOff(productSellers, filter.getFirstFilterValue());
            case "off percent" -> inOffPercentFilter(productSellers, filter.getFirstInt(), filter.getSecondInt());
            case "off seller" -> inSellerOff(productSellers, filter.getFirstFilterValue());
            case "off time" -> inOffTimeFilter(productSellers, filter.getFirstFilterValue(), filter.getSecondFilterValue());
            default -> false;
        };
    }

    private boolean inNotFinishedOff(ArrayList<ProductSeller> productSellers) {
        boolean returnValue = false;
        Date now = Controller.getCurrentTime();
        ArrayList<ProductSeller> temp = new ArrayList<>();
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.off != null && productSeller.off.getFinishTime().after(now)) {
                temp.add(productSeller);
                returnValue = true;
            }
        }
        productSellers.clear();
        productSellers.addAll(temp);
        return returnValue;
    }

    private boolean inOffTimeFilter(ArrayList<ProductSeller> productSellers, String start, String finish) {
        boolean returnValue = false;
        try {
            Date startDate = Controller.getDateWithString(start);
            Date finishDate = Controller.getDateWithString(finish);
            ArrayList<ProductSeller> temp = new ArrayList<>();
            for (ProductSeller productSeller : productSellers) {
                if (productSeller.off != null && productSeller.off.getStartTime().after(startDate) &&
                        productSeller.off.getFinishTime().before(finishDate)) {
                    temp.add(productSeller);
                    returnValue = true;
                }

            }
            productSellers.clear();
            productSellers.addAll(temp);
            return returnValue;
        } catch (DateException e) {
            return false;
        }
    }

    private boolean inSellerOff(ArrayList<ProductSeller> productSellers, String username) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.off != null) {
                if (productSeller.seller.getUsername().equalsIgnoreCase(username)) {
                    productSellers.clear();
                    productSellers.add(productSeller);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inOffPercentFilter(ArrayList<ProductSeller> productSellers, int min, int max) {
        boolean returnValue = false;
        ArrayList<ProductSeller> temp = new ArrayList<>();
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.off != null) {
                int percent = productSeller.off.getPercent();
                if (min < percent && max > percent) {
                    temp.add(productSeller);
                    returnValue = true;
                }
            }
        }
        productSellers.clear();
        productSellers.addAll(temp);
        return returnValue;
    }

    private boolean inOff(ArrayList<ProductSeller> productSellers, String offId) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.off != null && productSeller.off.getOffId().equalsIgnoreCase(offId)) {
                productSellers.clear();
                productSellers.add(productSeller);
                return true;
            }
        }
        return false;
    }

    private boolean inOff() {
        for (ProductSeller productSeller : this.productSellers) {
            if (productSeller.off != null) return true;
        }
        return false;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    private boolean inPropertiesFilter(Filter filter) {
        try {
            SpecialProperty property = this.getProperty(filter.getFilterKey());
            if (property.getType().equalsIgnoreCase("text")) {
                return property.getValue().equalsIgnoreCase(filter.getFirstFilterValue());
            } else {
                return filter.getFirstDouble() <= property.getNumericValue() && filter.getSecondDouble() >= property.getNumericValue();
            }
        } catch (Exception e) {
            return false;
        }
    }

    private SpecialProperty getProperty(String filterKey) throws Exception {
        SpecialProperty temp = new SpecialProperty(filterKey);
        int index = specialProperties.indexOf(temp);
        try {
            return specialProperties.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("");
        }
    }

    private boolean inScoreFilter(Filter filter) {
        return filter.getFirstDouble() <= this.scoreAverage && filter.getSecondDouble() >= this.scoreAverage;
    }

    private boolean inPriceFilter(Filter filter) {
        return filter.getFirstDouble() <= this.getMinimumPrice() && filter.getSecondDouble() >= this.getMinimumPrice();
    }

    private boolean inSellerFilter(Filter filter) {
        return this.hasSeller(filter.getFirstFilterValue());
    }

    private boolean inNameFilter(Filter filter) {
        return name.toLowerCase().contains(filter.getFirstFilterValue().toLowerCase());
    }

    private boolean inAvailabilityFilter(Filter filter) {
        if (filter.getFirstFilterValue().equalsIgnoreCase("yes")) {
            return this.isAvailable();
        } else if (filter.getFirstFilterValue().equalsIgnoreCase("no")) {
            return !this.isAvailable();
        }
        return false;
    }

    private boolean inBrandFilter(Filter filter) {
        return this.company.toLowerCase().contains(filter.getFirstFilterValue().toLowerCase());
    }

    private boolean inCategoryFilter(Filter filter) {
        return this.getMainCategory().getName().equalsIgnoreCase(filter.getFirstFilterValue());
    }

    private boolean inSubcategoryFilter(Filter filter) {
        try {
            return this.getSubCategory().getName().equalsIgnoreCase(filter.getFirstFilterValue());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public ArrayList<SpecialProperty> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(ArrayList<SpecialProperty> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void removeProduct() {
        allProducts.remove(this);
        for (Seller seller : this.sellers) {
            seller.removeProduct(this);
        }
        for (ProductSeller productSeller : this.productSellers) {
            if (productSeller.getOff() != null) {
                productSeller.off.removeProduct(this);
            }
        }
        this.removeFromDatabase();
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
            if (sellerInfo.off == null) {
                productData.addSeller(sellerInfo.seller.getUsername(), null, sellerInfo.price,
                        sellerInfo.numberInStock);
            } else {
                productData.addSeller(sellerInfo.seller.getUsername(),
                        sellerInfo.off.getOffId(),
                        sellerInfo.price, sellerInfo.numberInStock);
            }
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
        productData.setFile(this.file);
        productData.setFileExtension(this.fileExtension);
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

    public void removeSellerFromProduct(Seller seller) {
        if (productSellers.size() == 1) {
            this.removeProduct();
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
        productInfo.setFile(this.file);
        productInfo.setFileExtension(this.fileExtension);
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

    public Category getCategory() {
        if (subCategory != null)
            return subCategory;
        return mainCategory;
    }

    static class ProductSeller implements Comparable {
        Off off;
        private Seller seller;
        private double price;
        private int numberInStock;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProductSeller that = (ProductSeller) o;

            return seller.equals(that.seller);
        }

        @Override
        public int hashCode() {
            return seller.hashCode();
        }

        public double getPriceWithOff() {
            if (off == null)
                return price;
            else if (off.isOffFinished() || !off.isOffStarted())
                return price;

            int offPercent = off.getPercent();
            if (offPercent < 1) {
                offPercent *= 100;
            }
            return price * ((100 - offPercent) / 100.0);
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

        @Override
        public int compareTo(Object o) {
            return seller.getUsername().compareTo(((ProductSeller) o).seller.getUsername());
        }
    }//end ProductSeller class

}//end Product class
