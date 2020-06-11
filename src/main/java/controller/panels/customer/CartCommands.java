package controller.panels.customer;

import controller.Command;
import model.ecxeption.CommonException;
import model.ecxeption.Exception;
import model.ecxeption.cart.CantIncreaseException;
import model.ecxeption.product.ProductDoesntExistException;
import model.others.Product;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;
import model.user.Seller;
import model.user.User;

import static controller.Controller.*;

public abstract class CartCommands extends Command {
    public static ViewCartCommand getViewCartCommand() {
        return ViewCartCommand.getInstance();
    }

    public static IncreaseCommand getIncreaseCommand() {
        return IncreaseCommand.getInstance();
    }

    public static DecreaseCommand getDecreaseCommand() {
        return DecreaseCommand.getInstance();
    }

    public static TotalPriceCommand getTotalPriceCommand() {
        return TotalPriceCommand.getInstance();
    }

    protected void changingProductInCart(String productId, String sellerUsername, String changingType) throws Exception {
        ShoppingCart shoppingCart = getShoppingCart();
        if (!shoppingCart.isProductInCart(productId, sellerUsername)) {
            throw new ProductDoesntExistException("There isn't any product with this id and this seller in your shopping cart!!");
        }

        Product product = Product.getProductWithId(productId);
        User seller = User.getUserByUsername(sellerUsername);
        if (!(seller instanceof Seller)) {
            throw new CommonException("There isn't any seller with this username!!");
        }

        switch (changingType) {
            case "increase" -> {
                if (!shoppingCart.canIncrease(product, (Seller) seller)) {
                    throw new CantIncreaseException();
                } else
                    shoppingCart.increaseProductInCart(product, (Seller) seller);
            }
            case "decrease" -> shoppingCart.decreaseProductInCart(product, (Seller) seller);
        }
    }

    protected ShoppingCart getShoppingCart() {
        ShoppingCart shoppingCart;
        if (getLoggedUser() == null)
            shoppingCart = ShoppingCart.getLocalShoppingCart();
        else
            shoppingCart = ((Customer) getLoggedUser()).getShoppingCart();
        return shoppingCart;
    }
}


class ViewCartCommand extends CartCommands {
    private static ViewCartCommand command;

    private ViewCartCommand() {
        this.name = "show products in cart";
    }

    public static ViewCartCommand getInstance() {
        if (command != null)
            return command;
        command = new ViewCartCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) {
        return viewProductsInCart();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewProductsInCart() {
        if (getLoggedUser() != null) {
            return sendAnswer(((Customer) getLoggedUser()).getShoppingCart().cartInfo());
        } else
            return sendAnswer(ShoppingCart.getLocalShoppingCart().cartInfo());
    }

}


class IncreaseCommand extends CartCommands {
    private static IncreaseCommand command;

    private IncreaseCommand() {
        this.name = "increase product in cart";
    }

    public static IncreaseCommand getInstance() {
        if (command != null)
            return command;
        command = new IncreaseCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("product id"), request.getHashMap().get("seller username"));
        increaseProductInCart(request.getHashMap().get("product id"), request.getHashMap().get("seller username"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void increaseProductInCart(String productId, String sellerUsername) throws Exception {
        changingProductInCart(productId, sellerUsername, "increase");
    }
}


class DecreaseCommand extends CartCommands {
    private static DecreaseCommand command;

    private DecreaseCommand() {
        this.name = "decrease product in cart";
    }

    public static DecreaseCommand getInstance() {
        if (command != null)
            return command;
        command = new DecreaseCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws Exception {
        containNullField(request.getHashMap().get("product id"), request.getHashMap().get("seller username"));
        decreaseProductInCart(request.getHashMap().get("product id"), request.getHashMap().get("seller username"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void decreaseProductInCart(String productId, String sellerUsername) throws Exception {
        changingProductInCart(productId, sellerUsername, "decrease");
    }
}


class TotalPriceCommand extends CartCommands {
    private static TotalPriceCommand command;

    private TotalPriceCommand() {
        this.name = "show total price of cart";
    }

    public static TotalPriceCommand getInstance() {
        if (command != null)
            return command;
        command = new TotalPriceCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) {
        return cartPrice();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage cartPrice() {
        ShoppingCart shoppingCart = getShoppingCart();
        return sendAnswer(shoppingCart.getTotalPrice());
    }
}
