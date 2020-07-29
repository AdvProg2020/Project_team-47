package model.bank;

import java.util.ArrayList;

public class Bank {
    private static Bank bank;
    private  ArrayList<Account> accounts;
    private  ArrayList<Token> tokens;
    private  ArrayList<Receipt> receipts;
    private  ArrayList<Transaction> transactions;
    public Bank() {
        accounts = new ArrayList<>();
        tokens = new ArrayList<>();
        receipts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void setReceipts(ArrayList<Receipt> receipts) {
        this.receipts = receipts;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Token findTokenWithId(int tokenId) {
        for (Token token : tokens) {
            if (token.getId() == tokenId) {
                return token;
            }
        }
        return null;
    }

    public boolean isUsernameAvailable(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean isIdValid(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordCorrect(String id, String password) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                if (account.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTokenValid(int tokenId) {
        for (Token token : tokens) {
            if (token.getId() == tokenId) {
                return true;
            }
        }
        return false;
    }

    public Account findAccountWithId(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    public Account findAccountWithUsername(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    public Receipt findReceiptWithId(int id) {
        for (Receipt receipt : receipts) {
            if (receipt.getReceiptId() == id) {
                return receipt;
            }
        }
        return null;
    }

    public boolean isReceiptIdValid(int id) {
        for (Receipt receipt : receipts) {
            if (receipt.getReceiptId() == id) {
                return true;
            }
        }
        return false;
    }
}
