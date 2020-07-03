package graphic.panel.manager;

import graphic.MainFX;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddCategoryPage extends PageController {
    private final ArrayList<SpecialProperty> properties = new ArrayList<>();
    @FXML
    private ToggleGroup type;
    @FXML
    private Text error;
    @FXML
    private TextField mainCategory;
    @FXML
    private TextField propertyKey;
    @FXML
    private TextField propertyUnit;
    @FXML
    private TextField name;
    @FXML
    private RadioButton textPropertyRadioButton;
    @FXML
    private VBox vBox;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/AddCategoryPage.fxml");
    }

    @FXML
    private void back() {
        MainFX.getInstance().click();
        MainFX.getInstance().back();
    }

    @FXML
    private void addProperty() {
        MainFX.getInstance().click();
        if (propertyKey.getText().isEmpty()) {
            setError("Property can't be empty!!");
        } else {
            if (textPropertyRadioButton.isSelected()) addTextProperty();
            else addNumericProperty();
            updateVBox();
        }
    }

    private void addNumericProperty() {
        properties.add(new SpecialProperty(propertyKey.getText(), -1, propertyUnit.getText()));
    }

    private void addTextProperty() {
        properties.add(new SpecialProperty(propertyKey.getText(), ""));
    }

    @FXML
    private void removeProperty() {
        properties.remove(new SpecialProperty(propertyKey.getText()));
        updateVBox();
    }

    @FXML
    private void addCategory() {
        MainFX.getInstance().click();
        ClientMessage request;
        if (mainCategory.getText().isEmpty()) {
            request = new ClientMessage("add main category");
        } else {
            request = new ClientMessage("add sub category");
        }
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("main category name", mainCategory.getText());
        reqInfo.put("name", name.getText());
        request.setHashMap(reqInfo);
        request.setProperties(properties);
        processAnswer(send(request));
    }

    private void processAnswer(ServerMessage answer) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            setError(answer.getErrorMessage());
        } else {
            properties.clear();
            clearPage();
        }
    }


    private void setError(String error) {
        this.error.setText(error);
        this.error.setVisible(true);
    }

    @Override
    public void clearPage() {
        mainCategory.setText("");
        name.setText("");
        propertyKey.setText("");
        propertyUnit.setText("");
        vBox.getChildren().clear();
    }

    @Override
    public void update() {
        error.setVisible(false);
        updateVBox();
    }

    private void updateVBox() {
        vBox.getChildren().clear();
        for (SpecialProperty property : properties) {
            if (property.getType().equalsIgnoreCase("text")) {
                Label label = new Label(property.getKey());
                label.setFont(new Font(18));
                vBox.getChildren().add(label);
            } else {
                HBox hBox = new HBox(10);
                hBox.setAlignment(Pos.CENTER);
                Label keyLabel = new Label(property.getKey());
                keyLabel.setFont(new Font(15));
                Label unitLabel = new Label(property.getUnit());
                unitLabel.setFont(new Font(15));
                hBox.getChildren().addAll(keyLabel, unitLabel);
                vBox.getChildren().addAll(hBox);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
