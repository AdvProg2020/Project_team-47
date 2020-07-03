package graphic.panel.seller.offs;

import graphic.MainFX;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddOffPage extends PageController {
    private static PageController controller;
    @FXML
    private Text error;
    @FXML
    private TextField productId;
    @FXML
    private VBox vBox;
    @FXML
    private TextField percent;
    @FXML
    private TextField startYear;
    @FXML
    private TextField finishYear;
    @FXML
    private TextField startMonth;
    @FXML
    private TextField finishMonth;
    @FXML
    private TextField startDay;
    @FXML
    private TextField finishDay;
    @FXML
    private TextField startHour;
    @FXML
    private TextField finishHour;
    @FXML
    private TextField startMinute;
    @FXML
    private TextField finishMinute;

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/off/add/AddOffPage.fxml");
    }

    public static PageController getInstance() {
        return controller;
    }

    @Override
    public void clearPage() {
        clearTextFields();
    }

    @Override
    public void update() {
        error.setVisible(false);
    }

    private void clearTextFields() {
        vBox.getChildren().clear();
        percent.setText("");
        startYear.setText("");
        startMonth.setText("");
        startDay.setText("");
        startHour.setText("");
        startMinute.setText("");
        finishYear.setText("");
        finishMonth.setText("");
        finishDay.setText("");
        finishHour.setText("");
        finishMinute.setText("");
        productId.setText("");
    }

    @FXML
    private void back() {
        MainFX.getInstance().click();
        MainFX.getInstance().back();
    }

    @FXML
    private void addOff() {
        MainFX.getInstance().click();
        if (timeHasError()) return;
        if (percentHasError()) return;
        if (vBox.getChildren().size() == 0) {
            error.setText("You should choose add at least one product!!");
            return;
        }
        ClientMessage request = new ClientMessage("add off");
        HashMap<String, String> requestHashMap = new HashMap<>();
        requestHashMap.put("start-time", startDay.getText() + "-" + startMonth.getText() + "-" +
                startYear.getText() + " " + startHour.getText() + ":" + startMinute.getText());
        requestHashMap.put("finish-time", finishDay.getText() + "-" + finishMonth.getText() + "-" +
                finishYear.getText() + " " + finishHour.getText() + ":" + finishMinute.getText());
        requestHashMap.put("percent", percent.getText());
        request.setHashMap(requestHashMap);
        ArrayList<String> productId = new ArrayList<>();
        for (Node child : vBox.getChildren()) {
            productId.add(((Label) child).getText());
        }
        request.setArrayList(productId);
        ServerMessage answer = send(request);
        checkAddOffAnswer(answer);
    }

    private boolean percentHasError() {
        if (percent.getText().isEmpty()) {
            error.setText("Percent field shouldn't be empty!!");
            error.setVisible(true);
            return true;
        } else {
            try {
                int percentValue = Integer.parseInt(percent.getText());
                if (percentValue < 0 || percentValue > 100) throw new Exception("Please enter valid percent!!");
            } catch (Exception | NumberFormatException e) {
                error.setText(e.getMessage());
                error.setVisible(true);
                return true;
            }
        }
        return false;
    }

    private void checkAddOffAnswer(ServerMessage answer) {
        if (answer.getType().equals("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            clearTextFields();
            vBox.getChildren().clear();
        }
    }

    private boolean timeHasError() {
        try {
            checkYear(startYear.getText());
            checkYear(finishYear.getText());
            checkMonth(startMonth.getText());
            checkMonth(finishMonth.getText());
            checkDay(startDay.getText());
            checkDay(finishDay.getText());
            checkHour(startHour.getText());
            checkHour(finishHour.getText());
            checkMinute(startMinute.getText());
            checkMinute(finishMinute.getText());
        } catch (Exception e) {
            error.setText(e.getMessage());
            error.setVisible(true);
            return true;
        }
        return false;
    }

    @FXML
    private void addProduct() {
        MainFX.getInstance().click();
        if (productId.getText().isEmpty()) {
            error.setText("Product id shouldn't be empty!!");
            error.setVisible(true);
            return;
        }
        for (Node child : vBox.getChildren()) {
            if ((child instanceof Label) && ((Label) child).getText().equalsIgnoreCase(productId.getText())) {
                error.setText("You already added this product!!");
                error.setVisible(true);
                return;
            }
        }
        Label label = new Label(productId.getText());
        label.setFont(new Font(18));
        vBox.getChildren().add(label);
        error.setVisible(false);
    }

    @FXML
    private void removeProduct() {
        MainFX.getInstance().click();
        if (productId.getText().isEmpty()) {
            error.setText("Product id shouldn't be empty!!");
            error.setVisible(true);
            return;
        }
        vBox.getChildren().removeIf(child -> (child instanceof Label) &&
                ((Label) child).getText().equalsIgnoreCase(productId.getText()));
        error.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
    }

    private void checkYear(String year) throws Exception {
        if (year.isEmpty()) throw new Exception("Year field shouldn't be empty!!");
        try {
            Integer.parseInt(year);
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkMonth(String monthString) throws Exception {
        if (monthString.isEmpty()) throw new Exception("Month field shouldn't be empty!!");
        try {
            int month = Integer.parseInt(monthString);
            if (month < 1 || month > 12)
                throw new Exception("Please enter valid month!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkDay(String dayString) throws Exception {
        if (dayString.isEmpty()) throw new Exception("Day field shouldn't be empty!!");
        try {
            int day = Integer.parseInt(dayString);
            if (day < 1 || day > 31)
                throw new Exception("Please enter valid day!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkHour(String hourString) throws Exception {
        if (hourString.isEmpty()) throw new Exception("Hour field shouldn't be empty!!");
        try {
            int hour = Integer.parseInt(hourString);
            if (hour < 0 || hour > 24)
                throw new Exception("Please enter valid hour!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

    private void checkMinute(String minuteString) throws Exception {
        if (minuteString.isEmpty()) throw new Exception("Minute field shouldn't be empty!!");
        try {
            int minute = Integer.parseInt(minuteString);
            if (minute < 0 || minute > 60)
                throw new Exception("Please enter valid minute!!");
        } catch (NumberFormatException e) {
            throw new Exception("Please enter valid number!!");
        }
    }

}
