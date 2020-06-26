package graphic.panel.customer.CustomerPurchaseHistory;

import graphic.GraphicView;
import graphic.PageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.send.receive.LogInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerPurchaseHistoryController extends PageController {
    private static PageController controller;

    public static ArrayList<LogInfo> purchaseHistoryArrayList;

    @FXML
    TableView<PurchaseHistoryTable> tableView;

    @FXML
    TableColumn<PurchaseHistoryTable, String> id;

    @FXML
    TableColumn<PurchaseHistoryTable, Date> date;

    @FXML
    TableColumn<PurchaseHistoryTable, Double> price;

    @FXML
    TableColumn<PurchaseHistoryTable, String> seller;

    ObservableList<PurchaseHistoryTable> data = FXCollections.observableArrayList();

    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerPurchaseHistoryController();
        }
        return controller;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setData();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        seller.setCellValueFactory(new PropertyValueFactory<>("seller"));

        tableView.setItems(data);

    }

    private void setData() {
        for (LogInfo sortedPlayer : CustomerPurchaseHistoryController.purchaseHistoryArrayList) {
            data.add(new PurchaseHistoryTable(sortedPlayer.getLogId(), sortedPlayer.getLogDate(), sortedPlayer.getPrice(), sortedPlayer.getSeller()    ));
        }
    }


    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
