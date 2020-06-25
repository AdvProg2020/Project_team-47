package graphic.panel.customer.CustomerPurchaseHistory;

import java.util.Date;

public class PurchaseHistoryTable {
    private final String id;
    private final Date date;
    private final double price;
    private final String seller;


    public PurchaseHistoryTable(String id, Date date, double price, String seller) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }
}
