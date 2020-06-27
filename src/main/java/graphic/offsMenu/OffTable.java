package graphic.offsMenu;

import javafx.scene.image.ImageView;

import java.util.Date;

public class OffTable {
    ImageView photo;
    String productId;
    String productName;
    double productPrice;
    Date remainedTime;
    double ProductScore;
    double discount;

    public OffTable(ImageView photo, String productId, String productName,
                    double productPrice, Date remainedTime, double productScore, double discount) {
        this.photo = photo;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.remainedTime = remainedTime;
        ProductScore = productScore;
        this.discount = discount;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public Date getRemainedTime() {
        return remainedTime;
    }

    public double getProductScore() {
        return ProductScore;
    }

    public double getDiscount() {
        return discount;
    }
}
