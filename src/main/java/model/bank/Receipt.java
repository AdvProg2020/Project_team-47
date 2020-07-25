package model.bank;

public class Receipt {
    private final String type; // type can be "deposit","withdraw" or "move"
    private final int receiptId;
    private final Token token;
    private final double money;
    private final String sourceId;
    private final String destId;
    private final String description;
    private boolean paid;

    public Receipt(String type, String tokenId, String money, String sourceId, String destId, String description) {
        this.type = type;
        this.token = Bank.getInstance().findTokenWithId(Integer.parseInt(tokenId));
        this.money = Double.parseDouble(money);
        this.sourceId = sourceId;
        this.destId = destId;
        this.description = description;
        Bank.getInstance().getReceipts().add(this);
        receiptId = Bank.getInstance().getReceipts().size();
        paid = false;
    }

    public String getType() {
        return type;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public Token getToken() {
        return token;
    }

    public double getMoney() {
        return money;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getDestId() {
        return destId;
    }

    public String getDescription() {
        return description;
    }
}
