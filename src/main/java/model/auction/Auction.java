package model.auction;

import controller.Controller;
import database.AuctionData;
import database.Database;
import model.ecxeption.product.ProductDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.others.Product;
import model.send.receive.AuctionInfo;
import model.user.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Auction {

    private static ArrayList<Auction> auctions = new ArrayList<>();

    private String seller;
    private String productId;
    private Date startTime;
    private Date finishTime;

    public static ArrayList<AuctionInfo> getAllAuctionsInfo() {
        ArrayList<AuctionInfo> auctionsInfo = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionsInfo.add(auction.auctionInfo());
        }
        return auctionsInfo;
    }

    public Auction(String seller, String productId, Date startTime, Date finishTime) {
        this.seller = seller;
        this.productId = productId;
        if (startTime.before(Controller.getCurrentTime())) {
            this.startTime = Controller.getCurrentTime();
        } else {
            this.startTime = startTime;
        }
        this.finishTime = finishTime;
        auctions.add(this);
    }

    public AuctionInfo auctionInfo() {
        try {
            AuctionInfo auctionInfo = new AuctionInfo(startTime, finishTime, Seller.getUserByUsername(seller).userInfoForSending(), Product.getProductWithId(productId).getProductInfo());
            return  auctionInfo;
        } catch (UserNotExistException | ProductDoesntExistException e) {
            // ignore
        }
        return null;
    }

    public void updateDatabase() {
        AuctionData auctionData = new AuctionData(seller, productId, startTime, finishTime);
        Database.addAuction(auctionData, productId);
    }
}
