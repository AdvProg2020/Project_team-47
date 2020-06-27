package graphic.panel.seller.log;

import graphic.GraphicView;
import graphic.PageController;
import graphic.panel.LogPane;
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
        ClientMessage request;
        if (GraphicView.getInstance().getAccountType().equalsIgnoreCase("seller")) {
            request = new ClientMessage("view sales history");
        } else {
            request = new ClientMessage("view orders");
        }
        logsInfo = send(request).getLogInfoArrayList();
        pagination.setPageCount((logsInfo.size() - 1) / 5 + 1);
        if (logsInfo.size() == 0) {
            return;
        }
        initializeAnchorPane();
        initializePagination();
    }

    private void initializeAnchorPane() {
        anchorPanes = new AnchorPane[logsInfo.size()];
        for (int i = 0; i < logsInfo.size(); i++) {
            try {
                LogPane.setLogInfo(logsInfo.get(i));
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/fxml/panel/seller/log/LogPane.fxml"));
                anchorPanes[i] = anchorPane;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


    private void initializePagination() {
        pagination.setPageFactory((Integer page) -> {
            VBox vBox = new VBox();
            for (int i = 0; i < 4 && i < anchorPanes.length; i++) {
                vBox.getChildren().add(anchorPanes[page * 5 + i]);
            }
            return vBox;
        });
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

}
