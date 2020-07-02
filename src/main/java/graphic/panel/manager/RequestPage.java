package graphic.panel.manager;

import graphic.MainFX;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import model.send.receive.ClientMessage;
import model.send.receive.RequestInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RequestPage extends PageController {
    private static RequestInfo request;
    @FXML
    private TextArea textArea;
    @FXML
    private Text error;

    public static Scene getScene(RequestInfo request) {
        RequestPage.request = request;
        return getScene();
    }

    private static Scene getScene() {
        return getScene("/fxml/panel/manager/RequestPage.fxml");
    }

    @FXML
    private void back() {
        MainFX.getInstance().back();
    }

    @FXML
    private void accept() {
        sendRequest("accept request");
    }

    @FXML
    private void decline() {
        sendRequest("decline request");
    }

    private void sendRequest(String type) {
        ClientMessage request = new ClientMessage(type);
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("id", RequestPage.request.getId());
        request.setHashMap(reqInfo);
        processAnswer(send(request));
    }

    private void processAnswer(ServerMessage answer) {
        if (answer.getType().equalsIgnoreCase("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            back();
            ManageRequestPage.getInstance().update();
        }
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
        update();
        textArea.setText(request.toString());
    }
}
