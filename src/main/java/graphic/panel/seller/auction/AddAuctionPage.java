package graphic.panel.seller.auction;

import graphic.MainFX;
import graphic.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddAuctionPage extends PageController {

    private static PageController controller;

    @FXML
    private TextField productId;
    @FXML
    private Text error;
    @FXML
    private TextField startYear;
    @FXML
    private TextField startMonth;
    @FXML
    private TextField startDay;
    @FXML
    private TextField startHour;
    @FXML
    private TextField startMinute;
    @FXML
    private TextField finishYear;
    @FXML
    private TextField finishMonth;
    @FXML
    private TextField finishDay;
    @FXML
    private TextField finishHour;
    @FXML
    private TextField finishMinute;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/auction/AddAuctionPage.fxml");
    }

    @FXML
    private void addAuction() {
        MainFX.getInstance().click();
        if (timeHasError()) return;
        if (productId.getText().isEmpty()) {
            error.setText("Please enter an id!!");
            error.setVisible(true);
            return;
        }
        ClientMessage request = new ClientMessage("add auction");
        HashMap<String, String> requestHashMap = new HashMap<>();
        requestHashMap.put("start-time", startDay.getText() + "-" + startMonth.getText() + "-" +
                startYear.getText() + " " + startHour.getText() + ":" + startMinute.getText());
        requestHashMap.put("finish-time", finishDay.getText() + "-" + finishMonth.getText() + "-" +
                finishYear.getText() + " " + finishHour.getText() + ":" + finishMinute.getText());
        requestHashMap.put("product id", productId.getText());
        request.setHashMap(requestHashMap);
        ServerMessage answer = send(request);
        checkAnswer(answer);
    }

    private void checkAnswer(ServerMessage answer) {
        if (answer.getType().equals("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            clearTextFields();
            MainFX.getInstance().back();
        }
    }

    @FXML
    public void back() {
        MainFX.getInstance().click();
        MainFX.getInstance().back();
    }

    @Override
    public void clearPage() {
        clearTextFields();
    }

    private void clearTextFields() {
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

    @Override
    public void update() {
        error.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;
        update();
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
