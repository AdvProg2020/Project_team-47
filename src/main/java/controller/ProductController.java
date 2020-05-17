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

        int flag = 0;
        if (product == null) {
            sendError("You can't do this in this menu!!");
            flag = 1;
        } else if (!(loggedUser instanceof Customer)) {
            sendError("You can't do this!!");
            flag = 1;
        } else if (!product.isUserInSellerList((Seller) seller)) {
            sendError("This seller doesn't have this product!!");
            flag = 1;
        } else if (product.getNumberInStock((Seller) seller) == 0) {
            sendError("This seller doesn't have this product now!!");
            flag = 1;
        }
        if (flag == 1)
            return;

        //check if user didn't logged in and add product to local cart or user cart
        if (loggedUser == null) {
            addToLocalCart((Seller) seller);
        } else {
            Customer customer = (Customer) loggedUser;
            customer.getShoppingCart().addToCart(product, (Seller) seller);
        }
        actionCompleted();
    }

    private static void addToLocalCart(Seller seller) {
        ShoppingCart.getLocalShoppingCart().addToCart(product, seller);
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
            sendAnswer(product.compare(secondProduct), "product");
        }
    }

    public static void comment() {
        if (product == null) {
            sendError("You should be on a product page!!");
        } else {
            sendAnswer(product.getAllCommentInfo(), "comment");
        }
    }

    public static void addComment(String title, String content) {
        if (product == null) {
            sendError("You should be on a product page!!");
        } else {
            product.addComment(title, content, ((Customer) loggedUser));
            actionCompleted();
        }
    }
}//end ProductController class
