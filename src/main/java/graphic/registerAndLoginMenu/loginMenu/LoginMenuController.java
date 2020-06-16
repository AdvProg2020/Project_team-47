package graphic.registerAndLoginMenu.loginMenu;

import graphic.GraphicView;
import graphic.PageController;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenuController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new LoginMenuController();
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

    public void loginAndRegister(MouseEvent mouseEvent) {
    }

    public void showTextFields(MouseEvent mouseEvent) {
    }

    public void products(MouseEvent mouseEvent) {
    }

    public void offs(MouseEvent mouseEvent) {
    }

    public void hideTextFields(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
