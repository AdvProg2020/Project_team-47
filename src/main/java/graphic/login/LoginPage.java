package graphic.login;

import graphic.MainFX;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.customer.CustomerPage;
import graphic.panel.manager.ManagerPage;
import graphic.panel.seller.SellerPage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.send.receive.UserInfo;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginPage extends PageController {
    private static boolean shouldBack;
    @FXML
    private GridPane loginPane;
    @FXML
    private TextField newPass;
    @FXML
    private TextField userForPass;
    @FXML
    private TextField code;
    @FXML
    private TextField email;
    @FXML
    private TextField userForCode;
    @FXML
    private AnchorPane forgotPane;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text error;

    public static Scene getScene() {
        shouldBack = false;
        return getScene("/fxml/login/LoginPage.fxml");
    }

    public static Scene getSceneWithBack() {
        shouldBack = true;
        return getScene("/fxml/login/LoginPage.fxml");
    }

    @Override
    public void clearPage() {
        newPass.setText("");
        userForCode.setText("");
        userForPass.setText("");
        code.setText("");
        email.setText("");
        username.setText("");
        password.setText("");
        error.setText("");
    }

    @Override
    public void update() {
        error.setVisible(false);
        loginPane.setVisible(true);
        forgotPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    public void back() {
        MainFX.getInstance().click();
        MainFX.getInstance().back();
    }

    public void login() {
        MainFX.getInstance().click();
        ClientMessage clientMessage = new ClientMessage("login");

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("username", username.getText());
        hashMap.put("password", password.getText());

        clientMessage.setHashMap(hashMap);
        ServerMessage answer = send(clientMessage);
        if (answer.getType().equals("Successful")) {
            successfulLogin(answer.getUserInfo());
        } else {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        }
    }

    private void successfulLogin(UserInfo userInfo) {
        String userType = userInfo.getType();

        new Thread(() -> MainFX.getInstance().setAvatar(PageController.byteToImage(userInfo.getAvatar()))).start();
        MainFX.getInstance().setMyUsername(username.getText());
        MainFX.getInstance().setAccountType(userType);
        MainFX.getInstance().setLoggedIn(true);
        if (shouldBack) {
            TemplatePage.getInstance().update();
            MainFX.getInstance().back();
        } else {
            switch (userType) {
                case "manager" -> MainFX.getInstance().changeScene(ManagerPage.getScene());
                case "seller" -> MainFX.getInstance().changeScene(SellerPage.getScene());
                case "customer" -> MainFX.getInstance().changeScene(CustomerPage.getScene());
            }
        }
    }

    public void forgotPass() {
        MainFX.getInstance().click();
        loginPane.setVisible(false);
        forgotPane.setVisible(true);
    }

    public void sendCode() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("new password");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("code", code.getText());
        reqInfo.put("username", userForPass.getText());
        reqInfo.put("new password", newPass.getText());
        request.setHashMap(reqInfo);
        processError(send(request));
    }

    public void changePass() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("forgot password");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("email", email.getText());
        reqInfo.put("username", userForCode.getText());
        request.setHashMap(reqInfo);
        processError(send(request));
    }

    public void goToLoginPane() {
        loginPane.setVisible(true);
        forgotPane.setVisible(false);
    }

    private void processError(ServerMessage answer) {
        if (answer.getType().equals("Error")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(answer.getErrorMessage());
            alert.showAndWait();
        } else {
            clearPage();
        }
    }
}
