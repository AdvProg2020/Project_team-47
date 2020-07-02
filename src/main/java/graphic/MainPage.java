package graphic;

import graphic.login.RegisterPage;
import graphic.login.LoginPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainPage extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new MainPage();
        }
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/MainPage.fxml");
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
        GraphicView.getInstance().changeScene(LoginPage.getScene());
    }

    public void register() {
        GraphicView.getInstance().changeScene(RegisterPage.getScene());
    }
}