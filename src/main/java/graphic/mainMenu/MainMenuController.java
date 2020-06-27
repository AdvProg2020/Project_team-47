package graphic.mainMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.registerAndLoginMenu.RegisterPage;
import graphic.registerAndLoginMenu.loginMenu.LoginMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new MainMenuController();
        }
        return controller;
    }


    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void products() throws IOException {
        GraphicView.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Products.fxml")));
    }

    @FXML
    private void offs() {

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

    public void login() {
        GraphicView.getInstance().changeScene(LoginMenuController.getScene());
    }

    public void register() {
        GraphicView.getInstance().changeScene(RegisterPage.getScene());
    }
}