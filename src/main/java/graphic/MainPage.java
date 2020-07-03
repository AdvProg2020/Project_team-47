package graphic;

import graphic.login.LoginPage;
import graphic.login.RegisterPage;
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
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(TemplatePage.getScene());
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
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(LoginPage.getScene());
    }

    public void register() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(RegisterPage.getScene());
    }
}