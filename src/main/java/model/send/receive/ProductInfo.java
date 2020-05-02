package model.send.receive;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductInfo extends ServerMessage {
    private int seenTime;
    private String id;
    private String name;
    private String status;
    private HashMap<String, Double> priceForSellers;
    private HashMap<String, Double> pricesWithOff;
    private ArrayList<String> sellersNames;
    private HashMap<String, Integer> numberOfProductSellerHas;
    private String category;
    private String subCategory;
    private HashMap<String, String> specialProperties;
    private String description;
    private double scoreAverage;

    public int getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(int seenTime) {
        this.seenTime = seenTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, Double> getPriceForSellers() {
        return priceForSellers;
    }

    public void setPriceForSellers(HashMap<String, Double> priceForSellers) {
        this.priceForSellers = priceForSellers;
    }

    public HashMap<String, Double> getPricesWithOff() {
        return pricesWithOff;
    }

    public void setPricesWithOff(HashMap<String, Double> pricesWithOff) {
        this.pricesWithOff = pricesWithOff;
    }

    public ArrayList<String> getSellersNames() {
        return sellersNames;
    }

    public void setSellersNames(ArrayList<String> sellersNames) {
        this.sellersNames = sellersNames;
    }

    public HashMap<String, Integer> getNumberOfProductSellerHas() {
        return numberOfProductSellerHas;
    }

    public void setNumberOfProductSellerHas(HashMap<String, Integer> numberOfProductSellerHas) {
        this.numberOfProductSellerHas = numberOfProductSellerHas;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public HashMap<String, String> getSpecialProperties() {
        return specialProperties;
    }

    public void setSpecialProperties(HashMap<String, String> specialProperties) {
        this.specialProperties = specialProperties;
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
}
