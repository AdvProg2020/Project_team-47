package graphicView.panel.seller.offs.show;

import controller.Controller;
import graphicView.PageController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.OffInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowOffPageController extends PageController {
    private static ShowOffPageController controller;
    private OffInfo offInfo;
    private String editType;
    @FXML
    private Label percentLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label startAtLabel;
    @FXML
    private Label finishAtLabel;
    @FXML
    private TextField percentTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField monthTextField;
    @FXML
    private TextField dayTextField;
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField minuteTextFiled;
    @FXML
    private Button submitButton;
    @FXML
    private TextField productIdTextField;
    @FXML
    private TableView<ProductId> table;
    @FXML
    private TableColumn<ProductId, String> productName;
    @FXML
    private TableColumn<ProductId, String> productId;
    @FXML
    private Text productError;
    @FXML
    private Text editError;


    public static ShowOffPageController getInstance() {
        return controller;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        if (offInfo != null) {
            table.setItems(null);
            table.setItems(getObservableList(offInfo.getProductsNameId()));
        }
        productError.setVisible(false);
        editError.setVisible(false);
        yearTextField.setVisible(false);
        monthTextField.setVisible(false);
        dayTextField.setVisible(false);
        hourTextField.setVisible(false);
        minuteTextFiled.setVisible(false);
        percentTextField.setVisible(false);
        submitButton.setVisible(false);
    }

    private ObservableList<ProductId> getObservableList(HashMap<String, String> productInfo) {
        ArrayList<ProductId> productId = new ArrayList<>();
        for (Map.Entry<String, String> entry : productInfo.entrySet()) {
            productId.add(new ProductId(entry.getKey(), entry.getValue()));
        }
        ObservableList<ProductId> productIdObservableList = FXCollections.observableArrayList();
        productIdObservableList.addAll(productId);
        return productIdObservableList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    }

    public void initializeOff(OffInfo offInfo) {
        this.offInfo = offInfo;
        this.percentLabel.setText("Percent: " + offInfo.getPercent());
        this.idLabel.setText("Off Id: " + offInfo.getOffId());
        this.startAtLabel.setText("Start At: " + Controller.getDateString(offInfo.getStartTime()));
        this.finishAtLabel.setText("Finish At: " + Controller.getDateString(offInfo.getFinishTime()));
    }

    @FXML
    private void submit() {
        if (editType.equals("percent")) {
            sendPercentEdit();
        } else {
            sendTimeEdit();
        }
    }

    private void sendTimeEdit() {
        try {
            checkYear();
            checkMonth();
            checkDay();
            checkHour();
            checkMinute();
            ClientMessage clientMessage = new ClientMessage("edit off");
            HashMap<String, String> reqInfo = new HashMap<>();
            reqInfo.put("off info", offInfo.getOffId());
            reqInfo.put("field", editType);
            reqInfo.put("change type", "");
            reqInfo.put("new value", dayTextField.getText() + "-" + monthTextField.getText() +
                    "-" + yearTextField.getText() + " " + hourTextField + ":" + minuteTextFiled);
            clientMessage.setHashMap(reqInfo);
            ServerMessage answer = send(clientMessage);
            if(answer.getType().equals("Error"))
                throw new Exception(answer.getErrorMessage());
            hideTextFields();
            editError.setVisible(false);
        } catch (Exception e) {
            editError.setText(e.getMessage());
            editError.setVisible(true);
        }
    }

    private void checkYear() throws Exception {
        if(yearTextField.getText().isEmpty()) throw new Exception("Year field shouldn't be empty!!");
        try {
            Integer.parseInt(yearTextField.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkMonth() throws Exception {
        if(monthTextField.getText().isEmpty()) throw new Exception("Month field shouldn't be empty!!");
        try {
            int month = Integer.parseInt(monthTextField.getText());
            if (month < 1 || month > 12)
                throw new Exception("Please enter valid month!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkDay() throws Exception {
        if(dayTextField.getText().isEmpty()) throw new Exception("Day field shouldn't be empty!!");
        try {
            int day = Integer.parseInt(dayTextField.getText());
            if (day < 1 || day > 31)
                throw new Exception("Please enter valid day!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkHour() throws Exception {
        if(hourTextField.getText().isEmpty()) throw new Exception("Hour field shouldn't be empty!!");
        try {
            int hour = Integer.parseInt(hourTextField.getText());
            if (hour < 0 || hour > 24)
                throw new Exception("Please enter valid hour!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkMinute() throws Exception {
        if (minuteTextFiled.getText().isEmpty()) throw new Exception("Minute field shouldn't be empty!!");
        try {
            int minute = Integer.parseInt(minuteTextFiled.getText());
            if (minute < 0 || minute > 60)
                throw new Exception("Please enter valid minute!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void sendPercentEdit() {
        if (percentTextField.getText().isEmpty()) {
            editError.setText("Percent field shouldn't be empty!!");
            editError.setVisible(true);
        } else {
            try {
                int percent = Integer.parseInt(percentTextField.getText());
                if (percent < 0 || percent > 100) throw new Exception("");

                ClientMessage clientMessage = new ClientMessage("edit off");
                HashMap<String, String> reqInfo = new HashMap<>();
                reqInfo.put("off info", offInfo.getOffId());
                reqInfo.put("field", editType);
                reqInfo.put("change type", "");
                reqInfo.put("new value", percentTextField.getText());
                clientMessage.setHashMap(reqInfo);
                ServerMessage answer = send(clientMessage);
                if (answer.getType().equals("Error")) throw new Exception(answer.getErrorMessage());
                hideTextFields();
                editError.setVisible(false);
            } catch (NumberFormatException e) {
                editError.setText("Please enter valid number!!");
                editError.setVisible(true);
            } catch (Exception e) {
                editError.setText(e.getMessage());
                editError.setVisible(true);
            }
        }
    }

    @FXML
    private void showProduct() {
        // TODO: 6/13/2020
    }

    @FXML
    private void addToOff() {
        changeOffProduct("add");
    }

    @FXML
    private void removeFromOff() {
        changeOffProduct("remove");
    }

    private void changeOffProduct(String type) {
        if (productIdTextField.getText().isEmpty()) {
            productError.setText("Please enter product id!!");
            productError.setVisible(true);
            return;
        }
        ClientMessage clientMessage = new ClientMessage("edit off");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("off info", offInfo.getOffId());
        reqInfo.put("field", "products");
        reqInfo.put("change type", type);
        reqInfo.put("new value", productIdTextField.getText());
        clientMessage.setHashMap(reqInfo);
        ServerMessage answer = send(clientMessage);
        if (answer.getType().equals("Error")) {
            productError.setText(answer.getErrorMessage());
            productError.setVisible(true);
        } else {
            productError.setVisible(false);
            productIdTextField.setText("Successful");
        }
    }

    @FXML
    private void editPercent() {
        this.editType = "percent";
        hideTextFields(false);
    }

    @FXML
    private void editStartTime() {
        this.editType = "start-time";
        hideTextFields(true);
    }

    @FXML
    private void editFinishTime() {
        this.editType = "finish-time";
        hideTextFields(true);
    }

    private void hideTextFields(boolean isItDateEdit) {
        percentTextField.setVisible(!isItDateEdit);
        yearTextField.setVisible(isItDateEdit);
        monthTextField.setVisible(isItDateEdit);
        dayTextField.setVisible(isItDateEdit);
        hourTextField.setVisible(isItDateEdit);
        minuteTextFiled.setVisible(isItDateEdit);
        submitButton.setVisible(true);
    }

    private void hideTextFields() {
        percentTextField.setVisible(false);
        yearTextField.setVisible(false);
        monthTextField.setVisible(false);
        dayTextField.setVisible(false);
        hourTextField.setVisible(false);
        minuteTextFiled.setVisible(false);
        submitButton.setVisible(false);
    }

    private static class ProductId {
        private SimpleStringProperty productName;
        private SimpleStringProperty productId;

        public ProductId(String productName, String productId) {
            this.productName = new SimpleStringProperty(productName);
            this.productId = new SimpleStringProperty(productId);
        }

        public String getProductName() {
            return productName.get();
        }

        public void setProductName(String productName) {
            this.productName.set(productName);
        }

        public SimpleStringProperty productNameProperty() {
            return productName;
        }

        public String getProductId() {
            return productId.get();
        }

        public void setProductId(String productId) {
            this.productId.set(productId);
        }

        public SimpleStringProperty productIdProperty() {
            return productId;
        }
    }

}
