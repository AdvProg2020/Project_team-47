package graphic.productMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.productMenu.menus.ProductCommentsController;
import graphic.productMenu.menus.ProductProperties;
import graphic.productMenu.menus.ProductSpecialProperties;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductPageController extends PageController {
    private static PageController controller;

    @FXML
    public TextField score;

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
        //score.setText("score  :    " + );
        score.setText("score  :    " + 4.5);
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

    public void showProperties(MouseEvent mouseEvent) {
        //todo amir send message to server
        GraphicView.getInstance().changeScene(ProductProperties.getScene());
    }

    public void showSpecialProperties(MouseEvent mouseEvent) {
        //todo amir send message to server
        GraphicView.getInstance().changeScene(ProductSpecialProperties.getScene());
    }

    public void showComments(MouseEvent mouseEvent) {
        GraphicView.getInstance().changeScene(ProductCommentsController.getScene());
    }

    public void score(MouseEvent mouseEvent) {
        Optional<String> inputUsername = GraphicView.getInstance().showAlertPage("enter score", "score");

        if (!inputUsername.get().equals("")) {
            //send message to controller
        }
    }

    public void addToCart(MouseEvent mouseEvent) {
        //todo amir add to cart request
    }
}
