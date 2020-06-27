package graphic.panel;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.send.receive.UserInfo;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AccountPage extends PageController {
    private static PageController controller;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label companyInfoLabel;
    @FXML
    private Text username;
    @FXML
    private Text firstName;
    @FXML
    private Text lastName;
    @FXML
    private Text phoneNumber;
    @FXML
    private Text email;
    @FXML
    private Text companyName;
    @FXML
    private TextArea companyInfo;
    @FXML
    private TextField editField;
    @FXML
    private TextField editValue;
    @FXML
    private Text error;
    @FXML
    private Text money;

    public static Scene getScene() {
        return getScene("/fxml/panel/AccountPage.fxml");
    }

    public static PageController getInstance() {
        return controller;
    }

    @Override
    public void clearPage() {
        error.setVisible(false);
        editField.setText("");
        editValue.setText("");
    }

    @Override
    public void update() {
        updateAccountInfo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        clearPage();
        update();
    }

    private void updateAccountInfo() {
        ClientMessage request = new ClientMessage("view personal info");
        UserInfo user = send(request).getUserInfo();
        username.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        if (!user.getType().equalsIgnoreCase("manager")) {
            money.setText("Money: " + user.getMoney());
        }
        if (user.getType().equalsIgnoreCase("seller")) {
            companyInfo.setVisible(true);
            companyName.setVisible(true);
            companyInfoLabel.setVisible(true);
            companyNameLabel.setVisible(true);
            companyInfo.setText(user.getCompanyInfo());
            companyName.setText(user.getCompanyName());
        } else {
            companyInfo.setVisible(false);
            companyName.setVisible(false);
            companyInfoLabel.setVisible(false);
            companyNameLabel.setVisible(false);
        }
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

    @FXML
    private void edit() {
        if (editValue.getText().isEmpty() || editField.getText().isEmpty()) {
            error.setText("Fields can't be empty!!");
            error.setVisible(true);
            return;
        }

        ClientMessage request = new ClientMessage("edit personal info");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("field", editField.getText());
        hashMap.put("new value", editValue.getText());
        request.setHashMap(hashMap);
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            update();
            clearPage();
        }
    }

}
