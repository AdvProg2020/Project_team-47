package graphic.panel;

import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.send.receive.LogInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class LogPane extends PageController {
    private static LogInfo logInfo;

    public static void setLogInfo(LogInfo logInfo) {
        LogPane.logInfo = logInfo;
    }

    @FXML
    private Label priceLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label idLabel;
    @FXML
    private GridPane productPane;

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priceLabel.setText("Price: " + logInfo.getPrice());
        phoneNumberLabel.setText("Phone" + logInfo.getPhoneNumber());
        addressLabel.setText("Address: " + logInfo.getAddress());
        idLabel.setText("Id: " + logInfo.getLogId());
        initializeGridPane(logInfo);
    }
    private void initializeGridPane(LogInfo logInfo) {
        for (int i = 0; i < productPane.getChildren().size() && i < logInfo.productsNumber(); i++) {
            ((Label) productPane.getChildren().get(i)).setText(logInfo.getProduct(i));
        }
    }

}
