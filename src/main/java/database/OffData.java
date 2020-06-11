package database;

import model.discount.Off;
import model.ecxeption.common.OffDoesntExistException;
import model.ecxeption.product.ProductDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.others.Product;
import model.user.Seller;
import model.user.User;

import java.util.ArrayList;
import java.util.Date;

public class OffData {
    private Date startTime;
    private Date finishTime;
    private int percent;
    private String id;
    private String offStatus;
    private String sellerUsername;
    private ArrayList<String> products;

    public OffData() {
        products = new ArrayList<>();
    }

    static void addOffs(ArrayList<OffData> offs) {
        if (offs == null)
            return;
        for (OffData off : offs) {
            off.createOff();
        }
    }

    static void connectRelations(ArrayList<OffData> offs) {
        for (OffData off : offs) {
            off.connectRelations();
        }
    }

    private void connectRelations() {
        try {
            Off off = Off.getOffById(this.id);
            off.setSeller((Seller) User.getUserByUsername(this.sellerUsername));
            for (String productId : this.products) {
                Product product = null;
                try {
                    product = Product.getProductWithId(productId);
                } catch (ProductDoesntExistException e) {
                    e.printStackTrace();
                    continue;
                }
                off.addProduct(product);
            }
        } catch (UserNotExistException | OffDoesntExistException e) {
            e.printStackTrace();
        }
    }

    private void createOff() {
        Off off = new Off(this.id, this.percent);
        off.setStartTime(this.startTime);
        off.setFinishTime(this.finishTime);
        off.setOffStatus(this.offStatus);
    }

    public void addToDatabase() {
        Database.addOff(this, this.id);
    }

    public void addProduct(String productId) {
        this.products.add(productId);
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOffStatus(String offStatus) {
        this.offStatus = offStatus;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

}
