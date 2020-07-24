package model.bank;

import java.util.ArrayList;

public class Bank {
    private static Bank bank;
    private final ArrayList<Account> accounts;
    private final ArrayList<Token> tokens;
    private final ArrayList<Receipt> receipts;
    public Bank() {
        accounts = new ArrayList<>();
        tokens = new ArrayList<>();
        receipts = new ArrayList<>();
    }

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
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

    public Token findTokenWithId(int tokenId) {
        for (Token token : tokens) {
            if (token.getTokenId() == tokenId) {
                return token;
            }
        }
        return null;
    }

    public boolean isUsernameAvailable(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean isIdValid(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordCorrect(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTokenValid(int tokenId) {
        for (Token token : tokens) {
            if (token.getTokenId() == tokenId) {
                return true;
            }
        }
        return false;
    }

    public Account findAccountWithId(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    public Account findAccountWithUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
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
