package graphic.panel.seller.offs;

import controller.Controller;
import graphic.GraphicView;
import graphic.PageController;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.send.receive.ClientMessage;
import model.send.receive.OffInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManageOffsPage extends PageController {
    private static PageController controller;
    private ArrayList<OffInfo> offsInfo;
    @FXML
    private TextField textField;
    @FXML
    private Text error;
    @FXML
    private TableView<OffTable> table;
    @FXML
    private TableColumn<OffTable, String> id;
    @FXML
    private TableColumn<OffTable, String> percent;
    @FXML
    private TableColumn<OffTable, String> start;
    @FXML
    private TableColumn<OffTable, String> finish;

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/off/ManageOffsPage.fxml");
    }

    public static PageController getInstance() {
        return controller;
    }


    @FXML
    private void showTextFields() {
        if (textField.isVisible())
            return;
        Thread thread = new Thread(() -> {
            textField.setVisible(true);
            createFadeAnimations(0, 1);
        });
        thread.start();
    }

    @FXML
    private void hideTextFields() {
        if (!textField.isVisible())
            return;
        Thread thread = new Thread(() -> {
            createFadeAnimations(1, 0);
            textField.setVisible(false);
        });
        thread.start();
    }

    @FXML
    private void back() {
        clearPage();
        GraphicView.getInstance().back();
    }

    @FXML
    private void showOff() {
        if (textField.getText().isEmpty()) {
            error.setText("Please enter an id!!");
            return;
        }
        HashMap<String, String> request = new HashMap<>();
        request.put("off id", textField.getText());
        ClientMessage clientMessage = new ClientMessage("view off seller");
        clientMessage.setHashMap(request);
        ServerMessage answer = send(clientMessage);
        if (answer.getType().equals("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            clearPage();
            ShowOffPage.getInstance().initializePage(answer.getOffInfo());
            GraphicView.getInstance().changeScene(ShowOffPage.getScene());
        }
    }

    @FXML
    private void addOff() {
        clearPage();
        GraphicView.getInstance().changeScene(AddOffPage.getScene());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        finish.setCellValueFactory(new PropertyValueFactory<>("finish"));
    }

    @Override
    public void clearPage() {
        textField.setVisible(false);
        error.setVisible(false);
        error.setText("");
        textField.setText("");
    }

    @Override
    public void update() {
        ClientMessage request = new ClientMessage("view offs seller");
        offsInfo = send(request).getOffInfoArrayList();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.setItems(null);
        table.setItems(getObservableList());
    }

    private FadeTransition[] getFadingAnimation() {
        FadeTransition[] fadeTransitions = new FadeTransition[2];
        fadeTransitions[0] = new FadeTransition(Duration.millis(600), textField);
        fadeTransitions[1] = new FadeTransition(Duration.millis(600), error);
        return fadeTransitions;
    }

    private void createFadeAnimations(double from, double to) {
        FadeTransition[] fadeTransitions = getFadingAnimation();
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.setFromValue(from);
            fadeTransition.setToValue(to);
        }
        playFadeAnimation(fadeTransitions);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playFadeAnimation(FadeTransition[] fadeTransitions) {
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.play();
        }
    }

    private ObservableList<OffTable> getObservableList() {
        ArrayList<OffTable> off = new ArrayList<>();
        for (OffInfo offInfo : offsInfo) {
            off.add(new OffTable(offInfo.getOffId(), "" + offInfo.getPercent(),
                    Controller.getDateString(offInfo.getStartTime()), Controller.getDateString(offInfo.getFinishTime())));
        }
        ObservableList<OffTable> offTableObservableList = FXCollections.observableArrayList();
        offTableObservableList.addAll(off);
        return offTableObservableList;
    }

    private static class OffTable {
        private final SimpleStringProperty id;
        private final SimpleStringProperty percent;
        private final SimpleStringProperty start;
        private final SimpleStringProperty finish;

        public OffTable(String id, String percent, String start, String finish) {
            this.id = new SimpleStringProperty(id);
            this.percent = new SimpleStringProperty(percent);
            this.start = new SimpleStringProperty(start);
            this.finish = new SimpleStringProperty(finish);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public String getPercent() {
            return percent.get();
        }

        public void setPercent(String percent) {
            this.percent.set(percent);
        }

        public SimpleStringProperty percentProperty() {
            return percent;
        }

        public String getStart() {
            return start.get();
        }

        public void setStart(String start) {
            this.start.set(start);
        }

        public SimpleStringProperty startProperty() {
            return start;
        }

        public String getFinish() {
            return finish.get();
        }

        public void setFinish(String finish) {
            this.finish.set(finish);
        }

        public SimpleStringProperty finishProperty() {
            return finish;
        }
    }

}
