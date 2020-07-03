package graphic.panel.manager;

import graphic.MainFX;
import graphic.PageController;
import graphic.TemplatePage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CreateManagerPage extends PageController {
    private static boolean shouldBack;
    private String usernameString;
    private String passwordString;
    private File image;
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
    private Text error;


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

    public void back() {
        MainFX.getInstance().click();
        TemplatePage.getInstance().update();
        MainFX.getInstance().back();
    }

    public void register() {
        MainFX.getInstance().click();
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
        request.setFile(PageController.imageToByte(image));
        request.setFileExtension(".jpg");
        processRegisterAnswer(send(request), username.getText(), password.getText());
    }

    private void processRegisterAnswer(ServerMessage answer, String usernameString, String passwordString) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            this.usernameString = usernameString;
            this.passwordString = passwordString;
            MainFX.getInstance().changeScene(ManageUsersPage.getScene());
        }
    }

    @FXML
    private void choosePic() {
        MainFX.getInstance().click();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg"));
        File imageFile = fileChooser.showOpenDialog(MainFX.getInstance().getWindow());
        try {
            image = imageFile;
            MainFX.getInstance().setAvatar(new Image(image.toURI().toString()));
        } catch (Exception e) {
            image = null;
        }
    }
}
