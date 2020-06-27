package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.customer.CustomerPage;
import graphic.panel.manager.ManagerPage;
import graphic.panel.seller.SellerPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CreateManagerPage extends PageController {
    private static boolean shouldBack;
    private String usernameString;
    private String passwordString;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private  TextField phoneNumber;
    @FXML private Text error;


    public static Scene getScene() {
        shouldBack = false;
        return getScene("/fxml/panel/manager/CreateManager.fxml");
    }

    public static Scene getSceneWithBack() {
        shouldBack = true;
        return getScene("/fxml/panel/manager/CreateManager.fxml");
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        error.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);
    }

    private void processConfirmAnswer(ServerMessage answer) {
        update();
        GraphicView.getInstance().setMyUsername(usernameString);
        GraphicView.getInstance().setLoggedIn(true);
        if (shouldBack) {
            back();
        } else {
            GraphicView.getInstance().changeScene(ManagerPage.getScene());
        }
    }

    public void back() {
        TemplatePage.getInstance().update();
        GraphicView.getInstance().back();
    }

    public void register( ) {
        ClientMessage request = new ClientMessage("create manager profile");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("password", password.getText());
        reqInfo.put("username", username.getText());
        reqInfo.put("email", email.getText());
        reqInfo.put("first-name", firstName.getText());
        reqInfo.put("last-name", lastName.getText());
        reqInfo.put("phone-number", phoneNumber.getText());
        reqInfo.put("type", "manager");
        request.setHashMap(reqInfo);
        processRegisterAnswer(send(request),username.getText(),password.getText());
    }

    private void processRegisterAnswer(ServerMessage answer,String usernameString,String passwordString) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            this.usernameString = usernameString;
            this.passwordString = passwordString;
            //processConfirmAnswer(answer);
            GraphicView.getInstance().changeScene(ManageUsersPage.getScene());
        }

    }
}
