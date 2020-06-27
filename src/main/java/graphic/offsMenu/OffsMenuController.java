package graphic.offsMenu;

import graphic.GraphicView;
import graphic.PageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.send.receive.OffInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OffsMenuController extends PageController {

    public static ArrayList<OffInfo> offs;
    private static PageController controller;
    @FXML
    TableView<OffTable> tableView;

    @FXML
    TableColumn<OffTable, String> photo;

    @FXML
    TableColumn<OffTable, Date> productId;

    @FXML
    TableColumn<OffTable, Double> productName;

    @FXML
    TableColumn<OffTable, Double> productPrice;

    @FXML
    TableColumn<OffTable, Double> remainedTime;

    @FXML
    TableColumn<OffTable, Date> ProductScore;

    @FXML
    TableColumn<OffTable, Double> discount;

    ObservableList<OffTable> data = FXCollections.observableArrayList();


    public static PageController getInstance() {
        if (controller == null) {
            controller = new OffsMenuController();
        }
        return controller;
    }

    public static Scene getScene() {
        Scene scene = getScene("/fxml/offs/offsMenu.fxml");
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        return scene;
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

        //photo.setPrefWidth(80);
        //photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        remainedTime.setCellValueFactory(new PropertyValueFactory<>("remainedTime"));
        ProductScore.setCellValueFactory(new PropertyValueFactory<>("ProductScore"));
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tableView.setItems(data);

    }

    private void setData() {
        /*for (OffInfo off : offs) {
            data.add(new OffTable(null, off.get))
        }
        for (LogInfo sortedPlayer : CustomerPurchaseHistoryController.purchaseHistoryArrayList) {
            data.add(new PurchaseHistoryTable(sortedPlayer.getLogId(), sortedPlayer.getLogDate(), sortedPlayer.getPrice(), sortedPlayer.getSeller()    ));
        }*/
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }


}
