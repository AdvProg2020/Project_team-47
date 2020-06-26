package graphic.productMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.mainMenu.MainMenuController;
import graphic.productMenu.menus.ProductCommentsController;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPageController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new ProductPageController();
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

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

    public void showProperties(MouseEvent mouseEvent) {
    }

    public void showSpecialProperties(MouseEvent mouseEvent) {
    }

    public void showComments(MouseEvent mouseEvent) {
        GraphicView.getInstance().changeScene(ProductCommentsController.getScene());
    }

    public void score(MouseEvent mouseEvent) {
    }

    public void addToCart(MouseEvent mouseEvent) {
    }
}
