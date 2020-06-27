package graphic.productMenu.menus;

import graphic.GraphicView;
import graphic.PageController;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductProperties extends PageController {

    private static PageController controller;
    public TextArea text;


    public static PageController getInstance() {
        if (controller == null) {
            controller = new ProductProperties();
        }
        return controller;
    }

    public static Scene getScene() {
        Scene scene = getScene("/fxml/product/productProperties.fxml");
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        return scene;
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
}
