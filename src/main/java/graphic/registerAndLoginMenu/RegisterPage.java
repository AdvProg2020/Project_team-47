package graphic.registerAndLoginMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.customer.CustomerPage;
import graphic.panel.manager.ManagerPage;
import graphic.panel.seller.SellerPage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterPage extends PageController {
    private static boolean shouldBack;
    private String userType;
    private String usernameString;
    private String passwordString;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField companyName;
    @FXML
    private TextArea companyInfo;
    @FXML
    private RadioButton customerButton;
    @FXML
    private RadioButton sellerButton;
    @FXML
    private ToggleGroup type;
    @FXML
    private RadioButton managerButton;
    @FXML
    private Text error;
    @FXML
    private TextField verificationCode;
    @FXML
    private Button confirm;


    public static Scene getScene() {
        shouldBack = false;
        return getScene("/fxml/registerAndLoginMenu/RegisterPage.fxml");
    }

    public static Scene getSceneWithBack() {
        shouldBack = true;
        return getScene("/fxml/registerAndLoginMenu/RegisterPage.fxml");
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
        verificationCode.setVisible(false);
        confirm.setVisible(false);
    }

    public void confirm() {
        ClientMessage request = new ClientMessage("confirm email");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("username", usernameString);
        reqInfo.put("password", passwordString);
        reqInfo.put("verification code", verificationCode.getText());
        request.setHashMap(reqInfo);
        processConfirmAnswer(send(request));
    }

    private void processConfirmAnswer(ServerMessage answer) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            update();
            GraphicView.getInstance().setMyUsername(usernameString);
            GraphicView.getInstance().setLoggedIn(true);
            GraphicView.getInstance().setAccountType(getAccountType());
            if (shouldBack) {
                back();
            } else {
                switch (userType) {
                    case "manager" -> GraphicView.getInstance().changeScene(ManagerPage.getScene());
                    case "seller" -> GraphicView.getInstance().changeScene(SellerPage.getScene());
                    case "customer" -> GraphicView.getInstance().changeScene(CustomerPage.getScene());
                }
            }
        }
    }

    private String getAccountType() {
        if (managerButton.isSelected()) {
            return "manager";
        } else if (sellerButton.isSelected()) {
            return "seller";
        } else if (customerButton.isSelected()) {
            return "customer";
        }
        return "shouldn't happen";
    }

    public void back() {
        TemplatePage.getInstance().update();
        GraphicView.getInstance().back();
    }

    public void register() {
        ClientMessage request = new ClientMessage("register");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("password", password.getText());
        reqInfo.put("username", username.getText());
        reqInfo.put("email", email.getText());
        reqInfo.put("first-name", firstName.getText());
        reqInfo.put("last-name", lastName.getText());
        reqInfo.put("phone-number", phoneNumber.getText());
        reqInfo.put("company-name", companyName.getText());
        reqInfo.put("company-info", companyInfo.getText());
        if (managerButton.isSelected()) {
            userType = "manager";
            reqInfo.put("type", "manager");
        } else if (sellerButton.isSelected()) {
            userType = "seller";
            reqInfo.put("type", "seller");
        } else if (customerButton.isSelected()) {
            userType = "customer";
            reqInfo.put("type", "customer");
        }
        request.setHashMap(reqInfo);
        processRegisterAnswer(send(request), username.getText(), password.getText());
    }

    private void processRegisterAnswer(ServerMessage answer, String usernameString, String passwordString) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            this.usernameString = usernameString;
            this.passwordString = passwordString;
            confirm.setVisible(true);
            verificationCode.setVisible(true);
            update();
        }

    }
}
