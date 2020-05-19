package controller.product;

import controller.Command;
import controller.Error;
import model.category.Category;
import model.others.Product;
import model.others.ShoppingCart;
import model.send.receive.ClientMessage;
import model.user.Customer;
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

    protected boolean canUserDo() {
        if (getLoggedUser() == null) {
            sendError(Error.NEED_LOGIN.getError());
            return false;
        } else if (!(getLoggedUser() instanceof Customer)) {
            sendError(Error.NEED_CUSTOMER.getError());
            return false;
        }
        return true;
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
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString()))
            return;
        showProduct(request.getFirstString());
    }

    private void showProduct(String id) {

        setProduct(Product.getProductWithId(id));
        if (product() == null) {
            sendError("There isn't any product with this id!!");
        } else {
            product().addSeenTime();
        }
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
    public void process(ClientMessage request) {
        switch (request.getRequest()) {
            case "digest":
                digest();
                break;
            case "product attributes":
                attributes();
                break;
            case "comments":
                comment();
                break;
        }
    }

    private void digest() {
        if (product() == null) {
            sendError("You can't do this in this menu!!");
            return;
        }
        sendAnswer(product().digest());
    }

    private void attributes() {
        if (product() == null) {
            sendError("You can't do this in this menu!!");
            return;
        }
        sendAnswer(product().attributes());
    }

    public void comment() {
        if (product() == null) {
            sendError("You should be on a product page!!");
        } else {
            sendAnswer(product().getAllCommentInfo(), "comment");
        }
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
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString()))
            return;
        addToCart(request.getFirstString());
    }

    private void addToCart(String sellerUsername) {
        User seller = User.getUserByUsername(sellerUsername);
        if (!(seller instanceof Seller)) {
            sendError("There isn't any seller with this username!!");
        }

        int flag = 0;
        if (product() == null) {
            sendError("You can't do this in this menu!!");
            flag = 1;
        } else if (!(getLoggedUser() instanceof Customer)) {
            sendError("You can't do this!!");
            flag = 1;
        } else if (!product().isUserInSellerList((Seller) seller)) {
            sendError("This seller doesn't have this product!!");
            flag = 1;
        } else if (product().getNumberInStock((Seller) seller) == 0) {
            sendError("This seller doesn't have this product now!!");
            flag = 1;
        }
        if (flag == 1)
            return;

        //check if user didn't logged in and add product to local cart or user cart
        if (getLoggedUser() == null) {
            addToLocalCart((Seller) seller);
        } else {
            Customer customer = (Customer) getLoggedUser();
            customer.getShoppingCart().addToCart(product(), (Seller) seller);
        }
        actionCompleted();
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
    public void process(ClientMessage request) {
        if (!canUserDo())
            return;
        if (containNullField(request.getFirstString(), request.getSecondString()))
            return;
        addComment(request.getFirstString(), request.getSecondString());
    }

    private void addComment(String title, String content) {
        if (product() == null) {
            sendError("You should be on a product page!!");
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
    public void process(ClientMessage request) {
        if (containNullField(request.getFirstString()))
            return;
        compare(request.getFirstString());
    }

    private void compare(String secondProductId) {
        if (product() == null) {
            sendError("You should be on a product page!!");
        } else if (Product.isThereProduct(secondProductId)) {
            sendError("Second product doesn't exist!!");
        } else {
            Product secondProduct = Product.getProductWithId(secondProductId);
            assert secondProduct != null;
            sendAnswer(product().compare(secondProduct), "product");
        }
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
    public void process(ClientMessage request) {
        viewCategories(request.getFirstString(), request.getSecondString());
    }

    private void viewCategories(String sortField, String sortDirection) {
        if (sortDirection != null && sortField != null) {
            sortDirection = sortDirection.toLowerCase();
            sortField = sortField.toLowerCase();
        }
        if (!checkSort(sortField, sortDirection, "category")) {
            sendError(Error.CANT_SORT.getError());
            return;
        }
        sendAnswer(Category.getAllCategoriesInfo(sortField, sortDirection), "category");
    }

}

