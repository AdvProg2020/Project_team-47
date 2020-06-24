package graphic.productsMenu;


import graphic.GraphicView;
import graphic.PageController;
import graphic.registerAndLoginMenu.registerMenu.customer.RegisterCustomerController;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsMenuController extends PageController {

    private static PageController controller;


    public static PageController getInstance() {
        if (controller == null) {
            controller = new ProductsMenuController();
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
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
