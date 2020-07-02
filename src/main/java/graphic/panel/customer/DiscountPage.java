package graphic.panel.customer;

import graphic.MainFX;
import graphic.PageController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.send.receive.ClientMessage;
import model.send.receive.DiscountCodeInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiscountPage extends PageController {
    private ArrayList<DiscountCodeInfo> codeInfos;
    @FXML
    private TableView<CodeTable> table;
    @FXML
    private TableColumn<CodeTable, Integer> percent;
    @FXML
    private TableColumn<CodeTable, String> start;
    @FXML
    private TableColumn<CodeTable, String> finish;
    @FXML
    private TableColumn<CodeTable, String> code;
    @FXML
    private TableColumn<CodeTable, Integer> maxUsableTime;
    @FXML
    private TableColumn<CodeTable, Integer> maxDiscountAmount;


    public static Scene getScene() {
        return getScene("/fxml/panel/customer/DiscountPage.fxml");
    }

    @FXML
    private void back() {
        MainFX.getInstance().back();
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        ClientMessage request = new ClientMessage("view discount codes customer");
        codeInfos = send(request).getDiscountCodeInfoArrayList();
        table.getItems().clear();
        table.getItems().addAll(getObservableList());
    }

    private ObservableList<CodeTable> getObservableList() {
        ArrayList<CodeTable> codes = new ArrayList<>();
        for (DiscountCodeInfo codeInfo : codeInfos) {
            codes.add(new CodeTable(codeInfo));
        }
        return FXCollections.observableArrayList(codes);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        start.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        finish.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        maxUsableTime.setCellValueFactory(new PropertyValueFactory<>("maxUsableTime"));
        maxDiscountAmount.setCellValueFactory(new PropertyValueFactory<>("maxDiscountAmount"));
        update();
    }


    public static class CodeTable {
        private final SimpleStringProperty startTime;
        private final SimpleStringProperty finishTime;
        private final SimpleIntegerProperty percent;
        private final SimpleStringProperty code;
        private final SimpleIntegerProperty maxUsableTime;
        private final SimpleIntegerProperty maxDiscountAmount;

        public CodeTable(DiscountCodeInfo discount) {
            this.startTime = new SimpleStringProperty(discount.getStartTime().toString());
            this.finishTime = new SimpleStringProperty(discount.getFinishTime().toString());
            this.percent = new SimpleIntegerProperty(discount.getPercent());
            this.code = new SimpleStringProperty(discount.getCode());
            this.maxUsableTime = new SimpleIntegerProperty(discount.getMaxUsableTime());
            this.maxDiscountAmount = new SimpleIntegerProperty(discount.getMaxDiscountAmount());
        }

        public String getStartTime() {
            return startTime.get();
        }

        public void setStartTime(String startTime) {
            this.startTime.set(startTime);
        }

        public SimpleStringProperty startTimeProperty() {
            return startTime;
        }

        public String getFinishTime() {
            return finishTime.get();
        }

        public void setFinishTime(String finishTime) {
            this.finishTime.set(finishTime);
        }

        public SimpleStringProperty finishTimeProperty() {
            return finishTime;
        }

        public int getPercent() {
            return percent.get();
        }

        public void setPercent(int percent) {
            this.percent.set(percent);
        }

        public SimpleIntegerProperty percentProperty() {
            return percent;
        }

        public String getCode() {
            return code.get();
        }

        public void setCode(String code) {
            this.code.set(code);
        }

        public SimpleStringProperty codeProperty() {
            return code;
        }

        public int getMaxUsableTime() {
            return maxUsableTime.get();
        }

        public void setMaxUsableTime(int maxUsableTime) {
            this.maxUsableTime.set(maxUsableTime);
        }

        public SimpleIntegerProperty maxUsableTimeProperty() {
            return maxUsableTime;
        }

        public int getMaxDiscountAmount() {
            return maxDiscountAmount.get();
        }

        public void setMaxDiscountAmount(int maxDiscountAmount) {
            this.maxDiscountAmount.set(maxDiscountAmount);
        }

        public SimpleIntegerProperty maxDiscountAmountProperty() {
            return maxDiscountAmount;
        }
    }
}
