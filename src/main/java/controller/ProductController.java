package controller;

import model.others.Product;
import model.others.ShoppingCart;
import model.user.Customer;
import model.user.Seller;
import model.user.User;

public class ProductController extends Controller {
    private static Product product;

    public static void showProduct(String id) {
        product = Product.getProductWithId(id);
        if (product == null) {
            sendError("There isn't any product with this id!!");
        } else {
            product.addSeenTime();
        }
    }

    public static void digest() {
        if (product == null) {
            sendError("You can't do this in this menu!!");
            return;
        }
        sendAnswer(product.digest());
    }

    public static void addToCart(String sellerUsername) {
        User seller = User.getUserByUsername(sellerUsername);
        if (!(seller instanceof Seller)) {
            sendError("There isn't any seller with this username!!");
        }
        if (loggedUser == null) {
            assert seller instanceof Seller;
            addToLocalCart((Seller) seller);
            return;
        }
        if (product == null) {
            sendError("You can't do this in this menu!!");
        } else if (!(loggedUser instanceof Customer)) {
            sendError("You can't do this!!");
        } else if (!product.isUserInSellerList((Seller) seller)) {
            sendError("This seller doesn't have this product!!");
        } else if (product.getNumberInStock((Seller) seller) == 0) {
            sendError("This seller doesn't have this product now!!");
        } else {
            Customer customer = (Customer) loggedUser;
            assert seller instanceof Seller;
            customer.getShoppingCart().addToCart(product, (Seller) seller);
            sendAnswer("Action completed successfully.");
        }
    }

    private static void addToLocalCart(Seller seller) {
        if (product == null) {
            sendError("You can't do this in this menu!!");
        } else if (!product.isUserInSellerList(seller)) {
            sendError("This seller doesn't have this product!!");
        } else if (product.getNumberInStock(seller) == 0) {
            sendError("This seller doesn't have this product now!!");
        } else {
            ShoppingCart.getLocalShoppingCart().addToCart(product, seller);
            sendAnswer("Action completed successfully.");
        }
    }

    public static void attributes() {
        if (product == null) {
            sendError("You can't do this in this menu!!");
            return;
        }
        sendAnswer(product.attributes());
    }

    public static void compare(String secondProductId) {
        if (product == null) {
            sendError("You should be on a product page!!");
        } else if (Product.isThereProduct(secondProductId)) {
            sendError("Second product doesn't exist!!");
        } else {
            Product secondProduct = Product.getProductWithId(secondProductId);
            assert secondProduct != null;
            sendAnswer(product.compare(secondProduct));
        }
    }

    public static void comment() {
        if (product == null) {
            sendError("You should be on a product page!!");
        } else {
            sendAnswer(product.getAllCommentInfo());
        }
    }

    public static void addComment(String title, String content) {
        if (product == null) {
            sendError("You should be on a product page!!");
        } else {
            product.addComment(title, content, ((Customer) loggedUser));
        }
    }
}
