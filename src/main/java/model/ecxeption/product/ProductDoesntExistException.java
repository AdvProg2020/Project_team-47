package model.ecxeption.product;

import model.ecxeption.Exception;

public class ProductDoesntExistException extends Exception {
    public ProductDoesntExistException() {
        super("There isn't any product with this id!!");
    }

    public ProductDoesntExistException(String message) {
        super(message);
    }
}
