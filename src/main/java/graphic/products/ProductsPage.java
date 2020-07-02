package graphic.products;

import graphic.PageController;
import graphic.panel.ProductPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.others.ClientFilter;
import model.send.receive.ClientMessage;
import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductsPage extends PageController {

    @FXML
    private RadioButton noneOff;
    @FXML
    private ToggleGroup offGroup;
    @FXML
    private RadioButton beInOff;
    @FXML
    private RadioButton notFinishedOff;
    @FXML
    private TextField start;
    @FXML
    private TextField finish;
    @FXML
    private TextField maxPercent;
    @FXML
    private TextField minPercent;
    @FXML
    private ToggleGroup categoryGroup;
    @FXML
    private TextField offId;
    @FXML
    private TextField offSeller;
    @FXML
    private Text error;
    @FXML
    private RadioButton mainCategory;
    @FXML
    private RadioButton subCategory;
    @FXML
    private VBox vBox;
    @FXML
    private RadioButton none;
    @FXML
    private ToggleGroup sortField;
    @FXML
    private RadioButton score;
    @FXML
    private RadioButton seenTime;
    @FXML
    private RadioButton name;
    @FXML
    private RadioButton price;
    @FXML
    private RadioButton ascending;
    @FXML
    private ToggleGroup sortDirection;
    @FXML
    private RadioButton descending;
    @FXML
    private TextField minPrice;
    @FXML
    private TextField maxPrice;
    @FXML
    private TextField minScore;
    @FXML
    private TextField maxScore;
    @FXML
    private TextField brand;
    @FXML
    private TextField nameFilter;
    @FXML
    private TextField seller;
    @FXML
    private TextField category;
    @FXML
    private RadioButton available;
    @FXML
    private ToggleGroup availableFilter;
    @FXML
    private RadioButton notAvailable;
    @FXML
    private RadioButton disable;
    @FXML
    private Pagination pagination;

    @Override
    public void clearPage() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientMessage request = new ClientMessage("initialize all product page");
        send(request);
        update();
        addListener();
    }

    private void addListener() {
        addListenerToPrice();
        addListenerToScore();
        addListenerToOffTime();
        addListenerToPercent();
        addListenerToTextFilters();
    }

    private void addListenerToPercent() {
        minPercent.textProperty().addListener((observable, oldValue, newValue) -> percentListener(oldValue, newValue));
        maxPercent.textProperty().addListener((observable, oldValue, newValue) -> percentListener(oldValue, newValue));
    }

    private void percentListener(String oldValue, String newValue) {
        if (oldValue.equals(newValue)) return;
        if (intError(newValue)) return;
        if (minPercent.getText().isEmpty() || maxPercent.getText().isEmpty()) disableFilter("off percent");
        else addFilter("off percent", minPercent.getText(), maxPercent.getText());
    }

    private void addListenerToOffTime() {
        start.textProperty().addListener((observable, oldValue, newValue) -> timeListener(oldValue, newValue));
        finish.textProperty().addListener((observable, oldValue, newValue) -> timeListener(oldValue, newValue));
    }

    private void timeListener(String oldValue, String newValue) {
        if (oldValue.equals(newValue)) return;
        if (start.getText().isEmpty() || finish.getText().isEmpty()) disableFilter("off time");
        else addFilter("price", start.getText(), finish.getText());
    }

    private void addListenerToTextFilters() {
        brand.textProperty().addListener((observable, oldValue, newValue) -> textFilters("brand", oldValue, newValue));
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> textFilters("name", oldValue, newValue));
        seller.textProperty().addListener((observable, oldValue, newValue) -> textFilters("seller", oldValue, newValue));
        offSeller.textProperty().addListener((observable, oldValue, newValue) -> textFilters("off seller", oldValue, newValue));
        offId.textProperty().addListener((observable, oldValue, newValue) -> textFilters("be in special off", oldValue, newValue));
        category.textProperty().addListener((observable, oldValue, newValue) -> {
            if (mainCategory.isSelected()) textFilters("category", oldValue, newValue);
            else if (subCategory.isSelected()) textFilters("sub-category", oldValue, newValue);
            updateFilters();
        });
    }

    private void textFilters(String field, String oldValue, String newValue) {
        if (oldValue.equals(newValue)) return;
        if (newValue.isEmpty()) disableFilter(field);
        else addFilter(field, newValue, "");
    }

    private void addListenerToScore() {
        minScore.textProperty().addListener((observable, oldValue, newValue) -> scoreListener(oldValue, newValue));
        maxScore.textProperty().addListener((observable, oldValue, newValue) -> scoreListener(oldValue, newValue));
    }

    private void scoreListener(String oldValue, String newValue) {
        if (oldValue.equals(newValue)) return;
        if (intError(newValue)) return;
        if (minScore.getText().isEmpty() || maxScore.getText().isEmpty()) disableFilter("score");
        else addFilter("score", minScore.getText(), maxScore.getText());
    }

    private boolean intError(String number) {
        if (number.isEmpty()) return false;
        try {
            Integer.parseInt(number);
            return false;
        } catch (NumberFormatException e) {
            showNumberAlert();
            return true;
        }
    }

    private void addListenerToPrice() {
        minPrice.textProperty().addListener((observable, oldValue, newValue) -> priceListener(oldValue, newValue));
        maxPrice.textProperty().addListener((observable, oldValue, newValue) -> priceListener(oldValue, newValue));
    }

    private void priceListener(String oldValue, String newValue) {
        if (oldValue.equals(newValue)) return;
        if (doubleError(newValue)) return;
        if (minPrice.getText().isEmpty() || maxPrice.getText().isEmpty()) disableFilter("price");
        else addFilter("price", minPrice.getText(), maxPrice.getText());
    }

    private boolean doubleError(String number) {
        if (number.isEmpty()) return false;
        try {
            Double.parseDouble(number);
            return false;
        } catch (NumberFormatException e) {
            showNumberAlert();
            return true;
        }
    }

    private void showNumberAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Need Number");
        alert.setContentText("Please enter a number!");
        alert.showAndWait();
    }

    private void addFilter(String key, String first, String second) {
        ClientMessage request = new ClientMessage("filter an available filter products");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("filter key", key);
        reqInfo.put("first filter value", first);
        reqInfo.put("second filter value", second);
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }

    private void disableFilter(String key) {
        ClientMessage request = new ClientMessage("disable a selected filter products");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("filter key", key);
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }


    @Override
    public void update() {
        error.setVisible(false);
        initializePagination();
    }

    private void initializePagination() {
        ClientMessage request = new ClientMessage("show products products");
        initializeProducts(send(request).getProductInfoArrayList());
    }

    private void initializeProducts(ArrayList<ProductInfo> products) {
        pagination.setPageCount((products.size() - 1) / 5 + 1);
        pagination.setPageFactory((Integer pageNum) -> pageInitialize(pageNum, products));
    }

    private VBox pageInitialize(int pageNum, ArrayList<ProductInfo> products) {
        VBox vBox = new VBox();
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    ProductPane.setProductPage(products.get(pageNum * 5 + i));
                    vBox.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/panel/ProductPane.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return vBox;
    }


    @FXML
    private void sort() {
        if (none.isSelected()) {
            disableSort();
        } else if (score.isSelected()) {
            sort("score");
        } else if (seenTime.isSelected()) {
            sort("seen-time");
        } else if (name.isSelected()) {
            sort("name");
        } else if (price.isSelected()) {
            sort("price");
        }
    }

    private void disableSort() {
        ClientMessage request = new ClientMessage("disable sort products");
        sendAndProcess(request);
    }

    private void sort(String field) {
        ClientMessage request = new ClientMessage("sort an available sort products");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("field", field);
        if (ascending.isSelected()) {
            reqInfo.put("direction", "ascending");
        } else if (descending.isSelected()) {
            reqInfo.put("direction", "descending");
        }
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }

    @FXML
    private void availableFilter() {
        if (disable.isSelected()) {
            disableFilter("availability");
        } else if (available.isSelected()) {
            addFilter("availability", "yes", "");
        } else if (notAvailable.isSelected()) {
            addFilter("availability", "no", "");
        }
    }

    private void updateFilters() {
        ClientMessage request = new ClientMessage("show available filters products");
        ServerMessage answer = send(request);
        vBox.getChildren().clear();
        for (ClientFilter filter : answer.getFilters()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            Label key = new Label(filter.getName());
            key.setFont(new Font(15));
            TextField textField = new TextField();
            textField.setFont(new Font(12));
            textField.setPromptText(filter.getType());
            hBox.getChildren().addAll(key, textField);
            if (filter.getType().equals("numeric")) {
                textField.setPromptText("min");
                TextField secondTextField = new TextField();
                secondTextField.setPromptText("max");
                secondTextField.setFont(textField.getFont());
                Label unit = new Label(filter.getUnit());
                unit.setFont(new Font(13));
                hBox.getChildren().addAll(secondTextField, unit);
                addListenerForCategoryNumericFilter(textField, secondTextField, filter);
            } else {
                textField.textProperty().addListener((observable, oldValue, newValue) ->
                        categoryFilter(filter.getName(), oldValue, newValue));
            }
            vBox.getChildren().add(hBox);
        }
    }

    private void addListenerForCategoryNumericFilter(TextField first, TextField second, ClientFilter filter) {
        for (int i = 0; i < 2; i++) {
            TextField temp;
            if (i == 1) temp = first;
            else temp = second;
            temp.textProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue.equals(newValue)) return;
                if (newValue.isEmpty() || second.getText().isEmpty()) {
                    disableFilter(filter.getName());
                    return;
                } else if (doubleError(first.getText()) || doubleError(second.getText())) return;
                addFilter(filter.getName(), first.getText(), second.getText());
            });
        }
    }

    private void categoryFilter(String key, String oldValue, String newValue) {
        if (oldValue.equals(newValue)) return;
        if (newValue.isEmpty()) {
            disableFilter(key);
            return;
        }
        addFilter(key, newValue, "");
    }

    private void sendAndProcess(ClientMessage request) {
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            error.setText(answer.getErrorMessage());
            error.setVisible(true);
        } else {
            update();
        }
    }

    public void beInOff() {
        if (noneOff.isSelected()) {
            disableFilter("be in off");
            disableFilter("not finished off");
        } else if (beInOff.isSelected()) {
            addFilter("be in off", "", "");
        } else if (notFinishedOff.isSelected()) {
            addFilter("not finished off", "", "");
        }
    }

}
