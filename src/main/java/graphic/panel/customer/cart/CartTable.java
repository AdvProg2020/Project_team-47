package graphic.panel.customer.cart;

public class CartTable {
    private final String name;
    private final String sellerId;
    private final double price;
    private final int count;
    private final double totalPrice;

    public CartTable(String name, double price, int count, double totalPrice, String sellerId) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getSellerId() {
        return sellerId;
    }
}
