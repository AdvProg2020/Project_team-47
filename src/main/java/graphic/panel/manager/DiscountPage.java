package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.send.receive.ClientMessage;
import model.send.receive.DiscountCodeInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DiscountPage extends PageController {
    private static DiscountCodeInfo codeInfo;

    public static Scene getScene(DiscountCodeInfo code) {
        codeInfo = code;
        return getScene();
    }

    private static Scene getScene() {
        return getScene("/fxml/panel/manager/DiscountPage.fxml");
    }

    @FXML
    private Label percent;
    @FXML
    private Label startAt;
    @FXML
    private Label finishAt;
    @FXML
    private Label maxAmount;
    @FXML
    private Label usableTime;
    @FXML
    private Label code;

    @FXML
    private VBox usersVBox;

    @FXML
    private void back() {

    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        percent.setText(""+codeInfo.getPercent());
        code.setText(codeInfo.getCode());
        usableTime.setText("" + codeInfo.getMaxUsableTime());
        maxAmount.setText("" + codeInfo.getMaxDiscountAmount());
        startAt.setText(codeInfo.getStartTime().toString());
        finishAt.setText(codeInfo.getFinishTime().toString());
        usersVBox.getChildren().clear();
        for (String username : codeInfo.getUsersAbleToUse()) {
            Label userLabel = new Label(username);
            userLabel.setFont(new Font(17));
            usersVBox.getChildren().add(userLabel);
        }
    }
}
