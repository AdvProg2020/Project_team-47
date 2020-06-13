package graphicView.panel.seller.products;

import graphicView.PageController;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProductsPageController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        return controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }
}
