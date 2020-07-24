package database;

import model.auction.Auction;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.others.SpecialProperty;

import java.util.ArrayList;
import java.util.Date;

public class AuctionData {

    private String seller;
    private String productId;
    private Date startTime;
    private Date finishTime;

    public AuctionData(String seller, String productId, Date startTime, Date finishTime) {
        this.seller = seller;
        this.productId = productId;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    static void connectRelations(ArrayList<AuctionData> auctions) {
        for (AuctionData auction : auctions) {
            auction.connectRelations();
        }
    }

    private void connectRelations() {
        try {
            Product.getProductWithId(productId).getSpecialProperties().add(new SpecialProperty("Auction", "this product is in auction event"));
        } catch (ProductDoesntExistException e) {
            // ignore
        }
    }

    public static void addAuctions(ArrayList<AuctionData> auctions) {
        if (auctions == null)
            return;
        for (AuctionData auction : auctions) {
            auction.createAuction();
        }
    }

    private void createAuction() {
        new Auction(seller, productId, startTime, finishTime);
    }
}
