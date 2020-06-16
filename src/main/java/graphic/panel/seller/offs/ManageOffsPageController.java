package graphic.panel.seller.offs;

import controller.Controller;
import graphic.GraphicView;
import graphic.PageController;
import graphic.panel.seller.offs.add.AddOffPage;
import graphic.panel.seller.offs.show.ShowOffPage;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

public class ManageOffsPageController extends PageController {
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


    public static PageController getInstance() {
        return controller;
    }


    @FXML
    private void showTextFields() {
        //this function will use to show fields when user move mouse to new game button
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
        //this function will use to hide new game fields when user move mouse to other buttons except
        //new game button
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
            ((ShowOffPage)ShowOffPage.getInstance()).initializePage(answer.getOffInfo());
            GraphicView.getInstance().changeScene(ShowOffPage.getInstance());
        }
    }

    @FXML
    private void addOff() {
        clearPage();
        GraphicView.getInstance().changeScene(AddOffPage.getInstance());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        ClientMessage request = new ClientMessage("view offs seller");
        offsInfo = send(request).getOffInfoArrayList();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
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
            off.add(new OffTable(offInfo.getOffId(), ""+offInfo.getPercent(),
                    Controller.getDateString(offInfo.getStartTime()),Controller.getDateString(offInfo.getFinishTime())));
        }
        ObservableList<OffTable> offTableObservableList = FXCollections.observableArrayList();
        offTableObservableList.addAll(off);
        return offTableObservableList;
    }

    private static class OffTable{
        private SimpleStringProperty id;
        private SimpleStringProperty percent;
        private SimpleStringProperty start;
        private SimpleStringProperty finish;

        public String getId() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getPercent() {
            return percent.get();
        }

        public SimpleStringProperty percentProperty() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent.set(percent);
        }

        public String getStart() {
            return start.get();
        }

        public SimpleStringProperty startProperty() {
            return start;
        }

        public void setStart(String start) {
            this.start.set(start);
        }

        public String getFinish() {
            return finish.get();
        }

        public SimpleStringProperty finishProperty() {
            return finish;
        }

        public void setFinish(String finish) {
            this.finish.set(finish);
        }

        public OffTable(String id, String percent, String start, String finish) {
            this.id = new SimpleStringProperty(id);
            this.percent = new SimpleStringProperty(percent);
            this.start = new SimpleStringProperty(start);
            this.finish = new SimpleStringProperty(finish);
        }
    }

}
