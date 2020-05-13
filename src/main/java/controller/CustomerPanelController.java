package controller;

import model.log.BuyLog;
import model.others.Product;
import model.others.ShoppingCart;
import model.user.Customer;
import model.user.Seller;
import model.user.User;

public class CustomerPanelController extends UserPanelController {
    public static void viewBalance() {
        sendAnswer(((Customer) loggedUser).getMoney());
    }

    public static void viewProductsInCart() {
        if (loggedUser != null) {
            sendAnswer(((Customer) loggedUser).getShoppingCart().cartInfo());
        } else
            sendAnswer(ShoppingCart.getLocalShoppingCart().cartInfo());
    }

    public static void increaseProductInCart(String productId, String sellerUsername) {
        changingProductInCart(productId, sellerUsername, "increase");
    }

    public static void decreaseProductInCart(String productId, String sellerUsername) {
        changingProductInCart(productId, sellerUsername, "decrease");
    }

    private static void changingProductInCart(String productId, String sellerUsername, String changingType) {
        ShoppingCart shoppingCart = getShoppingCart();
        if (!shoppingCart.isProductInCart(productId, sellerUsername)) {
            sendError("There isn't any product with this id and this seller in your shopping cart!!");
            return;
        }

        Product product = Product.getProductWithId(productId);
        User seller = User.getUserByUsername(sellerUsername);
        if (!(seller instanceof Seller)) {
            sendError("There isn't any seller with this username!!");
            return;
        } else if (product == null) {
            sendError("There isn't any product with this id!!");
            return;
        }

        switch (changingType) {
            case "increase":
                if (!shoppingCart.canIncrease(product, (Seller) seller)) {
                    sendError("Can't increase this product!!");
                } else
                    shoppingCart.increaseProductInCart(product, (Seller) seller);
                break;
            case "decrease":
                shoppingCart.decreaseProductInCart(product, (Seller) seller);
                break;
        }
    }

    public static void cartPrice() {
        ShoppingCart shoppingCart = getShoppingCart();
        sendAnswer(shoppingCart.getTotalPrice());
    }

    public static void viewOrders() {
        sendAnswer(((Customer) loggedUser).getAllOrdersInfo(), "log");
    }

    public static void viewOrder(String orderId) {
        BuyLog buyLog = ((Customer) loggedUser).getBuyLog(orderId);
        if (buyLog == null) {
            sendError("You don't have any order with this id!!");
        } else {
            sendAnswer(buyLog.getLogInfoForSending());
        }
    }

    public static void rate(String productId, int score) {
        Product product = Product.getProductWithId(productId);
        if (score > 5 || score < 0) {
            sendError("Wrong score!!");
        } else if (product == null) {
            sendError("There isn't any product with this id!!");
        } else if (!((Customer) loggedUser).doesUserBoughtProduct(product)) {
            sendError("You should buy this product to rate it!!");
        } else {
            ((Customer) loggedUser).rate(score, product);
        }
    }

    public static void viewUserDiscountCode() {
        sendAnswer(((Customer) loggedUser).getAllDiscountCodeInfo(), "code");
    }

    private static ShoppingCart getShoppingCart() {
        ShoppingCart shoppingCart;
        if (loggedUser == null)
            shoppingCart = ShoppingCart.getLocalShoppingCart();
        else
            shoppingCart = ((Customer) loggedUser).getShoppingCart();
        return shoppingCart;
    }
}//end CustomerPanelController class
