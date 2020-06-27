package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.send.receive.CategoryInfo;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;
import model.send.receive.UserInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManageUsersPage extends PageController {

    public TextField username;

    @FXML
    private VBox vBox;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/ManageUsersPage.fxml");
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }



    @FXML
    private void CreateManager() {
        GraphicView.getInstance().changeScene(CreateManagerPage.getScene());
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        vBox.getChildren().clear();
        username.setText("");
        ClientMessage request = new ClientMessage("manage users");
        updateVBox(send(request).getUserInfoArrayList());
    }

    private void updateVBox(ArrayList<UserInfo> users) {
        for (UserInfo user : users) {
            Label userLabel = new Label(user.getUsername());
            userLabel.setFont(new Font(18));
            vBox.getChildren().add(userLabel);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    public void deleteUser(ActionEvent actionEvent) {
        if (!username.getText().equals("")) {
            ClientMessage request = new ClientMessage("delete user");
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username.getText());
            request.setHashMap(hashMap);
            ServerMessage answer = send(request);
            if (answer.getType().equals("Successful")) {
                update();
            } else {
                GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
            }
        }
    }
}
