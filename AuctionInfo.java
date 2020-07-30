package model.send.receive;

import java.util.Date;

public class AuctionInfo {

    private final Date startTime;
    private final Date finishTime;
    private final String seller;
    private final ProductInfo product;

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public String getSeller() {
        return seller;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public AuctionInfo(Date startTime, Date finishTime, String seller, ProductInfo product) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.seller = seller;
        this.product = product;
    }
}
