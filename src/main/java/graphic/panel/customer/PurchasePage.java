package graphic.panel.customer;

import graphic.PageController;
import graphic.TemplatePage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PurchasePage extends PageController {
    @FXML
    private
    Button useCodeButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button purchaseButton;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField otherRequests;
    @FXML
    private TextField phone;
    @FXML
    private TextField code;
    @FXML
    private TextField address;

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean sendAndProcess(ClientMessage request) {
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(answer.getErrorMessage());
            alert.showAndWait();
            return false;
        } else return true;
    }

    @FXML
    private void purchase() {
        if (sendAndProcess(new ClientMessage("pay"))) {
            TemplatePage.getInstance().back();
        }
    }

    @FXML
    private void submit() {
        ClientMessage request = new ClientMessage("get receiver information");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("address", address.getText());
        reqInfo.put("postal-code", postalCode.getText());
        reqInfo.put("phone-number", phone.getText());
        reqInfo.put("other-requests", otherRequests.getText());
        request.setHashMap(reqInfo);
        if (sendAndProcess(request)) {
            submitButton.setVisible(false);
            address.setVisible(false);
            postalCode.setVisible(false);
            phone.setVisible(false);
            otherRequests.setVisible(false);
            useCodeButton.setVisible(true);
            code.setVisible(true);
            purchaseButton.setVisible(true);
        }
    }

    @FXML
    private void useCode() {
        ClientMessage request = new ClientMessage("using discount code");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("code", code.getText());
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }
}
