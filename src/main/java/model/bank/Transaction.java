package model.bank;

public class Transaction {
    String type;
    Account source;
    Account dest;

    public Transaction(String type, Account source, Account dest) {
        this.type = type;
        this.source = source;
        this.dest = dest;
    }
}
