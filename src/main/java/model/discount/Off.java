package model.discount;

import controller.Controller;
import database.Database;
import database.OffData;
import model.others.Filter;
import model.others.Product;
import model.others.Sort;
import model.send.receive.OffInfo;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Off extends Discount {
    private static ArrayList<Off> allOffs;

    static {
        allOffs = new ArrayList<>();
    }

    private String offId;
    private String offStatus;
    private ArrayList<Product> products;
    private Seller seller;

    public Off() {
        super();
        products = new ArrayList<>();
        allOffs.add(this);
        this.offId = offIdCreator();
        this.offStatus = "CONFIRMED";
    }

    public Off(String offId, int percent) {
        products = new ArrayList<>();
        allOffs.add(this);
        this.offId = offId;
        this.percent = percent;
    }

    public static ArrayList<OffInfo> getAllOffsInfo() {
        ArrayList<OffInfo> offsInfo = new ArrayList<>();
        for (Off off : allOffs) {
            offsInfo.add(off.offInfo());
        }
        return offsInfo;
    }

    public static boolean isThereOff(String offId) {
        return allOffs.stream().anyMatch(off -> offId.equalsIgnoreCase(off.offId));
    }

    public static Off getOffById(String offId) {
        return allOffs.stream().filter(off -> offId.equalsIgnoreCase
                (off.offId)).findAny().orElse(null);
    }

    public static ArrayList<OffInfo> getAllProductsInOffsInfo(String sortField, String direction, ArrayList<Filter> filters) {
        ArrayList<OffInfo> offsInfo = new ArrayList<>();
        ArrayList<Off> intendedOffs = new ArrayList<>();

        for (Off off : allOffs) {
            if (off.inFilter(filters)) {
                intendedOffs.add(off);
            }
        }

        intendedOffs = Sort.sortOffs(sortField, direction, intendedOffs);

        for (Off off : intendedOffs) {
            offsInfo.add(off.offInfo());
        }
        return offsInfo;
    }

    private boolean inFilter(ArrayList<Filter> filters) {
        for (Filter filter : filters) {
            if (!this.inFilter(filter))
                return false;
        }
        return true;
    }

    private boolean inFilter(Filter filter) {
        switch (filter.getFilterKey()) {
            case "time":
                return inTimeFilter(filter);
            case "percent":
                return inPercentFilter(filter);
            case "seller":
                return inSellerFilter(filter);
            case "off-status":
                return inStatusFilter(filter);
        }
        return false;
    }

    private boolean inStatusFilter(Filter filter) {
        return this.offStatus.equalsIgnoreCase(filter.getFirstFilterValue());
    }

    private boolean inSellerFilter(Filter filter) {
        return this.seller.getUsername().toLowerCase().contains(filter.getFirstFilterValue().toLowerCase());
    }

    private boolean inPercentFilter(Filter filter) {
        int min = filter.getFirstInt();
        int max = filter.getSecondInt();
        return min <= this.percent && this.percent <= max;
    }

    private boolean inTimeFilter(Filter filter) {
        Date start = Controller.getDateWithString(filter.getFirstFilterValue());
        Date finish = Controller.getDateWithString(filter.getSecondFilterValue());
        return this.startTime.after(start) && this.finishTime.before(finish);
    }

    @Override
    public void removeFromDatabase() {
        Database.removeOff(this.offId);
    }

    public void updateDatabase() {
        OffData offData = new OffData();
        offData.setStartTime(this.startTime);
        offData.setFinishTime(this.finishTime);
        offData.setOffStatus(this.offStatus);
        offData.setId(this.offId);
        offData.setPercent(this.percent);
        offData.setSellerUsername(this.seller.getUsername());
        this.addProductsData(offData);
        offData.addToDatabase();
    }

    private void addProductsData(OffData offData) {
        for (Product product : this.products) {
            offData.addProduct(product.getId());
        }
    }

    private String offIdCreator() {
        String id = Controller.idCreator();
        if (isThereOff(id))
            return offIdCreator();
        else
            return id;
    }

    public boolean isOffStarted() {
        return startTime.before(Controller.getCurrentTime());
    }

    public boolean isOffFinished() {
        return finishTime.after(Controller.getCurrentTime());
    }

    @Override
    public String toString() {
        return null;
    }

    public void addProduct(Product product) {
        products.add(product);
        this.updateDatabase();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        this.updateDatabase();
    }

    public boolean isItInOff(Product product) {
        return products.contains(product);
    }

    public void removeOff() {
        allOffs.remove(this);
        this.removeFromDatabase();
    }

    public OffInfo offInfo() {
        OffInfo offInfo = new OffInfo(startTime, finishTime, percent);
        offInfo.setOffId(this.offId);
        offInfo.setOffStatus(this.offStatus);
        offInfo.setSellerUsername(seller.getUsername());
        for (Product product : products) {
            offInfo.addProduct(product);
        }
        return offInfo;
    }

    public String getOffId() {
        return offId;
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

}