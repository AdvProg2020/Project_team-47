package graphic.panel.manager;

import graphic.MainFX;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditCategoryPage extends PageController {
    @FXML
    private ToggleGroup propertyType;
    @FXML
    private ToggleGroup type;
    @FXML
    private Text error;
    @FXML
    private TextField name;
    @FXML
    private TextField propertyKey;
    @FXML
    private TextField propertyUnit;
    @FXML
    private TextField category;
    @FXML
    private RadioButton mainCategoryRadioButton;
    @FXML
    private RadioButton numericRadioButton;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/EditCategoryPage.fxml");
    }

    @FXML
    private void back() {
        MainFX.getInstance().back();
    }

    @FXML
    private void changeName() {
        ClientMessage request;
        if (mainCategoryRadioButton.isSelected()) {
            request = new ClientMessage("edit main category");
        } else {
            request = new ClientMessage("edit sub category");
        }
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("category name", category.getText());
        reqInfo.put("field", "name");
        reqInfo.put("new value", name.getText());
        request.setHashMap(reqInfo);
        processAnswer(send(request));
    }

    private void processAnswer(ServerMessage answer) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            update();
        }
    }

    @FXML
    private void addProperty() {
        changeProperty("add property");
    }

    @FXML
    private void removeProperty() {
        changeProperty("remove property");
    }

    private void changeProperty(String type) {
        ClientMessage request;
        if (mainCategoryRadioButton.isSelected()) request = new ClientMessage("edit main category");
        else request = new ClientMessage("edit sub category");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("category name", category.getText());
        reqInfo.put("new value", propertyKey.getText());
        reqInfo.put("unit", propertyUnit.getText());
        reqInfo.put("field", type);
        if (numericRadioButton.isSelected()) reqInfo.put("type", "numeric");
        else reqInfo.put("type", "text");
        request.setHashMap(reqInfo);
        processAnswer(send(request));
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        error.setVisible(false);
        name.setText("");
        propertyKey.setText("");
        propertyUnit.setText("");
        category.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
