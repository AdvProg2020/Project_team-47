package model.ecxeption.product;

import model.ecxeption.Exception;

public class CategoryDoesntExistException extends Exception {
    public CategoryDoesntExistException() {
        super("There isn't any category with this name!!");
    }
}
