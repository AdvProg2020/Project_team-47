package controller.product;

import controller.Command;
import controller.Controller;
import model.others.Product;
import model.send.receive.ClientMessage;

import java.util.ArrayList;

public class ProductController extends Controller {
    private static ProductController productController;
    private Product product;
    private ArrayList<Command> commands;

    private ProductController() {
        commands = new ArrayList<>();
        commands.add(ProductCommands.getAddCommentCommand());
        commands.add(ProductCommands.getAddToCartCommand());
        commands.add(ProductCommands.getCompareCommand());
        commands.add(ProductCommands.getProductInfoCommand());
        commands.add(ProductCommands.getShowProductCommand());
        commands.add(ProductCommands.getShowCategoriesCommand());
    }

    public static ProductController getInstance() {
        if (productController != null)
            return productController;
        productController = new ProductController();
        return productController;
    }


    public Product product() {
        if (loggedUser != null) {
            return loggedUser.getProductPage();
        } else {
            return this.product;
        }
    }

    void setProduct(Product product) {
        if (loggedUser != null) {
            loggedUser.setProductPage(product);
        } else {
            this.product = product;
        }
    }

}//end ProductController class
