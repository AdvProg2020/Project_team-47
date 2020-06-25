package graphic.panel.seller.log;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.send.receive.ClientMessage;
import model.send.receive.LogInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogsPage extends PageController {
    private static PageController controller;
    private ArrayList<LogInfo> logsInfo;
    private AnchorPane[] anchorPanes;
    @FXML
    private Pagination pagination;
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

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/log/LogsPage.fxml");
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
        ClientMessage request = new ClientMessage("view sales history");
        logsInfo = send(request).getLogInfoArrayList();
        pagination.setPageCount((logsInfo.size() - 1) / 4 + 1);
        if (logsInfo.size() == 0) {
            return;
        }
        initializeAnchorPane();
        initializePagination();
        VBox vBox = new VBox();
        vBox.getChildren().add(vBox);
    }

    private void initializeAnchorPane() {
        anchorPanes = new AnchorPane[logsInfo.size()];
        for (int i = 0; i < logsInfo.size(); i++) {
            AnchorPane anchorPane;
            try {
                anchorPane = FXMLLoader.load(getClass().getResource("../../../../../resources/fxml/panel/seller/log/LogPane.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            priceLabel.setText("Price: " + logsInfo.get(i).getPrice());
            phoneNumberLabel.setText("Phone" + logsInfo.get(i).getPhoneNumber());
            addressLabel.setText("Address: " + logsInfo.get(i).getAddress());
            idLabel.setText("Id: " + logsInfo.get(i).getLogId());
            initializeGridPane(logsInfo.get(i));
            anchorPanes[i] = anchorPane;
        }
    }

    private void initializeGridPane(LogInfo logInfo) {
        for (int i = 0; i < productPane.getChildren().size() && i < logInfo.productsNumber(); i++) {
            ((Label) productPane.getChildren().get(i)).setText(logInfo.getProduct(i));
        }
    }

    private void initializePagination() {
        pagination.setPageFactory((Integer page) -> {
            VBox vBox = new VBox();
            for (int i = 0; i < 4; i++) {
                vBox.getChildren().add(anchorPanes[page * 4 + i]);
            }
            return vBox;
        });
        pagination.setPageCount(10);
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

}
