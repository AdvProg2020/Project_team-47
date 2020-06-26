package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.send.receive.ClientMessage;
import model.send.receive.RequestInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageRequestPage extends PageController {
    private static PageController controller;
    public static Scene getScene() {
        return getScene("/fxml/panel/manager/ManageRequestPage.fxml");
    }

    @FXML
    private VBox vBox;

    public static PageController getInstance() {
        return controller;
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        ClientMessage request = new ClientMessage("manage requests");
        setVBox(send(request).getRequestArrayList());
    }

    private void setVBox(ArrayList<RequestInfo> requests) {
        vBox.getChildren().clear();
        for (RequestInfo request : requests) {
            Label requestLabel = new Label(request.getId());
            requestLabel.setFont(new Font(18));
            requestLabel.setOnMouseClicked(e -> onRequestClick(request));
            vBox.getChildren().add(requestLabel);
        }
    }

    private void onRequestClick(RequestInfo request) {
        GraphicView.getInstance().changeScene(RequestPage.getScene(request));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
        controller = this;
    }
}
