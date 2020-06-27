package graphic.registerAndLoginMenu.loginMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.customer.CustomerPage;
import graphic.panel.manager.ManagerPage;
import graphic.panel.seller.SellerPage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginMenuController extends PageController {
    private static boolean shouldBack;
    public static Scene getScene() {
        shouldBack = false;
        return getScene("/fxml/registerAndLoginMenu/loginMenu.fxml");
    }

    public static Scene getSceneWithBack() {
        shouldBack = true;
        return getScene("/fxml/registerAndLoginMenu/loginMenu.fxml");
    }

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text error;

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        error.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    public void back() {
        GraphicView.getInstance().back();
    }

    public void login() {
        ClientMessage clientMessage = new ClientMessage("login");

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("username", username.getText());
        hashMap.put("password", password.getText());

        clientMessage.setHashMap(hashMap);
        ServerMessage answer = send(clientMessage);
        if (answer.getType().equals("Successful")) {
            successfulLogin(answer.getFirstString());
        } else {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        }
    }

    private void successfulLogin(String userType) {
        GraphicView.getInstance().setMyUsername(username.getText());
        GraphicView.getInstance().setLoggedIn(true);
        if (shouldBack) {
            TemplatePage.getInstance().update();
            GraphicView.getInstance().back();
        } else {
            switch (userType) {
                case "manager" -> GraphicView.getInstance().changeScene(ManagerPage.getScene());
                case "seller" -> GraphicView.getInstance().changeScene(SellerPage.getScene());
                case "customer"-> GraphicView.getInstance().changeScene(CustomerPage.getInstance());
            }
        }
    }
}
