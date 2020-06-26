package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddDiscountPage extends PageController {
    @FXML
    private TextField percent;
    @FXML
    private TextField usableTime;
    @FXML
    private TextField startAt;
    @FXML
    private TextField finishAt;
    @FXML
    private TextField maxAmount;
    @FXML
    private VBox vBox;
    @FXML
    private Text error;
    @FXML
    private TextField username;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/AddDiscountPage.fxml");
    }

    @FXML
    private void giveGift() {
        // TODO: 6/25/2020
    }

    @FXML
    private void removeUser() {
        if (username.getText().isEmpty()) {
            error.setText("Username field shouldn't be empty!!");
            error.setVisible(true);
            return;
        }
        vBox.getChildren().removeIf(child -> (child instanceof Label) &&
                ((Label) child).getText().equalsIgnoreCase(username.getText()));
        error.setVisible(false);
    }

    @FXML
    private void addUser() {
        if (username.getText().isEmpty()) {
            error.setText("Username field shouldn't be empty!!");
            error.setVisible(true);
            return;
        }
        for (Node child : vBox.getChildren()) {
            if ((child instanceof Label) && ((Label) child).getText().equalsIgnoreCase(username.getText())) {
                error.setText("You already added this user!!");
                error.setVisible(true);
                return;
            }
        }
        Label label = new Label(username.getText());
        label.setFont(new Font(18));
        vBox.getChildren().add(label);
        error.setVisible(false);
    }

    @FXML
    private void addCode() {
        ClientMessage request = new ClientMessage("create discount code");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("start-time", startAt.getText());
        reqInfo.put("finish-time", finishAt.getText());
        reqInfo.put("percent", percent.getText());
        reqInfo.put("max-usable-time", usableTime.getText());
        reqInfo.put("max-discount-amount", maxAmount.getText());
        request.setHashMap(reqInfo);
        ArrayList<String> usernames = new ArrayList<>();
        for (Node child : vBox.getChildren()) {
            if (child instanceof Label) usernames.add(((Label) child).getText());
        }
        request.setArrayList(usernames);
        ServerMessage answer = send(request);
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            update();
        }
    }

    @FXML
    private void back() {
        ManageDiscountPage.getController().update();
        GraphicView.getInstance().back();
    }

    @Override
    public void clearPage() {
        username.setText("");
        percent.setText("");
        startAt.setText("");
        finishAt.setText("");
        maxAmount.setText("");
        usableTime.setText("");
        vBox.getChildren().clear();
    }

    @Override
    public void update() {
        error.setVisible(false);
        clearPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
