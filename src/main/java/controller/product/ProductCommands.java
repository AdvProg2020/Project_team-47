package controller.product;

import controller.Command;
import model.category.Category;
import model.ecxeption.CommonException;
import model.ecxeption.DebugException;
import model.ecxeption.Exception;
import model.ecxeption.common.NullFieldException;
import model.ecxeption.filter.InvalidSortException;
import model.ecxeption.product.ProductDoesntExistException;
import model.ecxeption.user.UserNotExistException;
import model.ecxeption.user.UserTypeException;
import model.others.Product;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.user.Customer;
import model.user.Manager;
import model.user.Seller;
import model.user.User;

import static controller.Controller.*;
import static controller.panels.UserPanelCommands.checkSort;

public abstract class ProductCommands extends Command {

    public static ShowProductCommand getShowProductCommand() {
        return ShowProductCommand.getInstance();
    }

    public static ProductInfoCommand getProductInfoCommand() {
        return ProductInfoCommand.getInstance();
    }

    public static AddToCartCommand getAddToCartCommand() {
        return AddToCartCommand.getInstance();
    }

    public static AddCommentCommand getAddCommentCommand() {
        return AddCommentCommand.getInstance();
    }

    public static CompareCommand getCompareCommand() {
        return CompareCommand.getInstance();
    }

    public static ShowCategoriesCommand getShowCategoriesCommand() {
        return ShowCategoriesCommand.getInstance();
    }

    protected Product product() {
        return ProductController.getInstance().product();
    }

    protected void setProduct(Product product) {
        ProductController.getInstance().setProduct(product);
    }

    protected void canUserDo() throws UserTypeException.NeedCustomerException {
        if (getLoggedUser() instanceof Manager || getLoggedUser() instanceof Seller)
            throw new UserTypeException.NeedCustomerException();
    }
}

class ShowProductCommand extends ProductCommands {
    private static ShowProductCommand command;

    private ShowProductCommand() {
        this.name = "(view product in cart|show product)";
    }

    public static ShowProductCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowProductCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, ProductDoesntExistException {
        containNullField(request.getHashMap().get("product id"));
        showProduct(request.getHashMap().get("product id"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void showProduct(String id) throws ProductDoesntExistException {
        setProduct(Product.getProductWithId(id));
        product().addSeenTime();
    }

}

class ProductInfoCommand extends ProductCommands {
    private static ProductInfoCommand command;

    private ProductInfoCommand() {
        this.name = "(digest|product attributes|comments)";
    }

    public static ProductInfoCommand getInstance() {
        if (command != null)
            return command;
        command = new ProductInfoCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws CommonException, DebugException {
        return switch (request.getType()) {
            case "digest" -> digest();
            case "product attributes" -> attributes();
            case "comments" -> comment();
            default -> throw new CommonException("Shouldn't happen!!");
        };
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage digest() throws DebugException {
        if (product() == null) {
            throw new DebugException();
        }
        return sendAnswer(product().digest());
    }

    private ServerMessage attributes() throws DebugException {
        if (product() == null) {
            throw new DebugException();
        }
        return sendAnswer(product().attributes());
    }

    public ServerMessage comment() throws DebugException {
        if (product() == null) throw new DebugException();
        return sendAnswer(product().getAllCommentInfo(), "comment");
    }

}


class AddToCartCommand extends ProductCommands {
    private static AddToCartCommand command;

    private AddToCartCommand() {
        this.name = "(view product in cart|show product)";
    }

    public static AddToCartCommand getInstance() {
        if (command != null)
            return command;
        command = new AddToCartCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, UserTypeException.NeedCustomerException, UserNotExistException, DebugException {
        containNullField("seller username");
        canUserDo();
        addToCart(request.getHashMap().get("seller username"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void addToCart(String sellerUsername) throws UserNotExistException, DebugException {
        User seller = User.getUserByUsername(sellerUsername);
        if (!(seller instanceof Seller)) {
            throw new UserNotExistException("Seller not exist!!");
        }
        if (product() == null) {
            throw new DebugException();
        } else if (!product().isUserInSellerList((Seller) seller)) {
            throw new DebugException();
        } else if (product().getNumberInStock((Seller) seller) == 0) {
            throw new DebugException();
        }

        //check if user didn't logged in and add product to local cart or user cart
        if (getLoggedUser() == null) {
            addToLocalCart((Seller) seller);
        } else {
            Customer customer = (Customer) getLoggedUser();
            customer.getShoppingCart().addToCart(product(), (Seller) seller);
        }
    }

    private void addToLocalCart(Seller seller) {
        ShoppingCart.getLocalShoppingCart().addToCart(product(), seller);
    }
}

class AddCommentCommand extends ProductCommands {
    private static AddCommentCommand command;

    private AddCommentCommand() {
        this.name = "add comment";
    }

    public static AddCommentCommand getInstance() {
        if (command != null)
            return command;
        command = new AddCommentCommand();
        return command;
    }

    @Override
    public ServerMessage process(ClientMessage request) throws UserTypeException.NeedCustomerException, NullFieldException, DebugException {
        canUserDo();
        if (getLoggedUser() == null)
            throw new UserTypeException.NeedCustomerException();
        containNullField(request.getHashMap().get("title"), request.getHashMap().get("content"));
        addComment(request.getHashMap().get("title"), request.getHashMap().get("content"));
        return actionCompleted();
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private void addComment(String title, String content) throws DebugException {
        if (product() == null) {
            throw new DebugException();
        } else {
            product().addComment(title, content, ((Customer) getLoggedUser()));
            actionCompleted();
        }
    }

}

class CompareCommand extends ProductCommands {
    private static CompareCommand command;

    private CompareCommand() {
        this.name = "compare products";
    }

    public static CompareCommand getInstance() {
        if (command != null)
            return command;
        command = new CompareCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws NullFieldException, ProductDoesntExistException, DebugException {
        containNullField(request.getHashMap().get("id"));
        return compare(request.getHashMap().get("id"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage compare(String secondProductId) throws DebugException, ProductDoesntExistException {
        if (product() == null) {
            throw new DebugException();
        }
        Product secondProduct = Product.getProductWithId(secondProductId);
        return sendAnswer(product().compare(secondProduct), "product");
    }

}

class ShowCategoriesCommand extends ProductCommands {
    private static ShowCategoriesCommand command;

    private ShowCategoriesCommand() {
        this.name = "view categories";
    }

    public static ShowCategoriesCommand getInstance() {
        if (command != null)
            return command;
        command = new ShowCategoriesCommand();
        return command;
    }


    @Override
    public ServerMessage process(ClientMessage request) throws InvalidSortException {
        return viewCategories(request.getHashMap().get("sort field"), request.getHashMap().get("sort direction"));
    }

    @Override
    public void checkPrimaryErrors(ClientMessage request) throws Exception {

    }

    private ServerMessage viewCategories(String sortField, String sortDirection) throws InvalidSortException {
        if (sortDirection != null && sortField != null) {
            sortDirection = sortDirection.toLowerCase();
            sortField = sortField.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "category"))
            throw new InvalidSortException();
        return sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection), "category");
    }

}

