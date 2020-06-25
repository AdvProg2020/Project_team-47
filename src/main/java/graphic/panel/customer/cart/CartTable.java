package graphic.panel.customer.cart;

public class CartTable {
    private String name;
    private double price;
    private int count;
    private double totalPrice;

    public CartTable(String name, double price, int count, double totalPrice) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
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
}
