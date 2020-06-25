package graphic.panel.customer.cart.purchasing;

import graphic.PageController;
import graphic.panel.customer.cart.CustomerCartController;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchasePageController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new PurchasePageController();
        }
        return controller;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
