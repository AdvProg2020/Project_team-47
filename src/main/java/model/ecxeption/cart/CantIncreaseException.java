package model.ecxeption.cart;

import model.ecxeption.Exception;

public class CantIncreaseException extends Exception {
    public CantIncreaseException() {
        super("Can't increase this product!!");
    }
}
