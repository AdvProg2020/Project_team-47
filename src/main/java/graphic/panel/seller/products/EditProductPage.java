package graphic.panel.seller.products;

import graphic.PageController;
import javafx.scene.Scene;
import model.send.receive.ProductInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProductPage extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/products/edit/EditProductPage.fxml");
    }

    public static void setProductInfo(ProductInfo productInfo) {

    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
    }
}
