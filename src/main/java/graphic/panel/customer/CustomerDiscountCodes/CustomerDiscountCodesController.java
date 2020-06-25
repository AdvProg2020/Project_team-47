package graphic.panel.customer.CustomerDiscountCodes;

import graphic.GraphicView;
import graphic.Page;
import graphic.PageController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryPage;
import graphic.panel.customer.CustomerPurchaseHistory.PurchaseHistoryTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.send.receive.DiscountCodeInfo;
import model.send.receive.LogInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerDiscountCodesController extends PageController {

    private static PageController controller;

    public static ArrayList<DiscountCodeInfo> discountCodes;

    @FXML
    TableView<DiscountCodesTable> tableView;

    @FXML
    TableColumn<DiscountCodesTable, Date> startTime;

    @FXML
    TableColumn<DiscountCodesTable, Date> finishTime;

    @FXML
    TableColumn<DiscountCodesTable, Integer> percent;

    @FXML
    TableColumn<DiscountCodesTable, String> code;

    @FXML
    TableColumn<DiscountCodesTable, Integer> maxUsableTime;

    @FXML
    TableColumn<DiscountCodesTable, Integer> maxDiscountAmount;


    ObservableList<DiscountCodesTable> data = FXCollections.observableArrayList();


    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerDiscountCodesController();
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
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        finishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        maxUsableTime.setCellValueFactory(new PropertyValueFactory<>("maxUsableTime"));
        maxDiscountAmount.setCellValueFactory(new PropertyValueFactory<>("maxDiscountAmount"));

        tableView.setItems(data);
    }

    private void setData() {

        for (DiscountCodeInfo discountCode : CustomerDiscountCodesController.discountCodes) {
            data.add(new DiscountCodesTable(discountCode.getStartTime(), discountCode.getFinishTime(),
                    discountCode.getPercent(), discountCode.getCode()
            , discountCode.getMaxUsableTime(), discountCode.getMaxDiscountAmount()));
        }
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

}
