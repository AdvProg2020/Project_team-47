package model.bank;

public class Receipt {
    private final String type; // type can be "deposit","withdraw" or "move"
    private final int receiptId;
    private final Token token;
    private final double money;
    private final int sourceId;
    private final int destId;
    private final String description;

    public Receipt(String type, String tokenId, String money, String sourceId, String destId, String description) {
        this.type = type;
        this.token = Bank.getInstance().findTokenWithId(Integer.parseInt(tokenId));
        this.money = Double.parseDouble(money);
        this.sourceId = Integer.parseInt(sourceId);
        this.destId = Integer.parseInt(destId);
        this.description = description;
        Bank.getInstance().getReceipts().add(this);
        receiptId = Bank.getInstance().getReceipts().size();
    }

    public String getType() {
        return type;
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

    public int getSourceId() {
        return sourceId;
    }

    public int getDestId() {
        return destId;
    }

    public String getDescription() {
        return description;
    }
}
