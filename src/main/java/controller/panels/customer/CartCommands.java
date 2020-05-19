package controller.panels.customer;

import controller.Command;
import model.others.Product;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
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

    protected void changingProductInCart(String productId, String sellerUsername, String changingType) {
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
    public void process(ClientMessage request) {
        viewProductsInCart();
    }

    private void viewProductsInCart() {
        if (getLoggedUser() != null) {
            sendAnswer(((Customer) getLoggedUser()).getShoppingCart().cartInfo());
        } else
            sendAnswer(ShoppingCart.getLocalShoppingCart().cartInfo());
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
    public void process(ClientMessage request) {
        if (containNullField(request.getArrayList().get(0), request.getArrayList().get(1)))
            return;
        increaseProductInCart(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void increaseProductInCart(String productId, String sellerUsername) {
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
    public void process(ClientMessage request) {
        if (containNullField(request.getArrayList().get(0), request.getArrayList().get(1)))
            return;
        decreaseProductInCart(request.getArrayList().get(0), request.getArrayList().get(1));
    }

    private void decreaseProductInCart(String productId, String sellerUsername) {
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
    public void process(ClientMessage request) {
        cartPrice();
    }

    private void cartPrice() {
        ShoppingCart shoppingCart = getShoppingCart();
        sendAnswer(shoppingCart.getTotalPrice());
    }
}
