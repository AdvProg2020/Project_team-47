package model.ecxeption.purchase;

import model.ecxeption.Exception;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super("You don't have enough money to buy this products!!");
    }
}
