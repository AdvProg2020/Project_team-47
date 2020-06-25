package graphic.panel.customer.CustomerPurchaseHistory;

public class Table {
    private final String username;
    private final Integer score;


    public Table(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }
}
