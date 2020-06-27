package graphic.panel.seller.products;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditProductPage extends PageController {
    private static ProductInfo product;
    private String chosenField;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private TextArea description;
    @FXML
    private Label numberInStock;
    @FXML
    private Label category;
    @FXML
    private Label price;
    @FXML
    private Label subCategory;
    @FXML
    private Label id;
    @FXML
    private VBox vBox;
    @FXML
    private Text error;
    @FXML
    private TextField editValue;
    private boolean propertyChange;


    public static Scene getScene() {
        return getScene("/fxml/panel/seller/products/edit/EditProductPage.fxml");
    }

    public static void setProductInfo(ProductInfo productInfo) {
        product = productInfo;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        error.setVisible(false);
        chosenField = null;
        propertyChange = false;
        editValue.setText("");
    }

    private void initializeVBox(ArrayList<SpecialProperty> properties) {
        vBox.getChildren().clear();
        if (properties.size() == 0) {
            Label empty = new Label("No Property to show!!");
            empty.setFont(new Font(20));
            vBox.getChildren().add(empty);
        }
        vBox.setSpacing(10);
        for (SpecialProperty property : properties) {
            HBox hBox = new HBox(8);
            Label keyLabel = new Label(property.getKey());
            keyLabel.setFont(new Font(15));
            hBox.getChildren().add(keyLabel);
            if (property.getType().equalsIgnoreCase("text")) {
                Label valueLabel = new Label(property.getValue());
                valueLabel.setFont(keyLabel.getFont());
                hBox.getChildren().add(valueLabel);
            } else {
                Label valueLabel = new Label("" + property.getNumericValue());
                Label unitLabel = new Label(property.getUnit());
                valueLabel.setFont(keyLabel.getFont());
                unitLabel.setFont(keyLabel.getFont());
                hBox.getChildren().addAll(valueLabel, unitLabel);
            }
            hBox.setOnMouseClicked(e -> {
                chosenField = keyLabel.getText();
                propertyChange = true;
            });
            vBox.getChildren().add(hBox);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeInfos();
        initializeLabelsFunction();
        initializeVBox(product.getSpecialProperties());
        update();
    }

    private void initializeInfos() {
        image.setImage(PageController.byteToImage(product.getFile()));
        price.setText("" + product.getPrice(GraphicView.getInstance().getMyUsername()));
        numberInStock.setText("" + product.getNumberInStock(GraphicView.getInstance().getMyUsername()));
        id.setText(product.getId());
        description.setText(product.getDescription());
        subCategory.setText(product.getSubCategory());
        category.setText(product.getMainCategory());
        name.setText(product.getName());
    }

    private void initializeLabelsFunction() {
        price.setOnMouseClicked(e -> {
            propertyChange = false;
            chosenField = "price";
        });
        numberInStock.setOnMouseClicked(e -> {
            chosenField = "number-of-product";
            propertyChange = false;
        });
        description.setOnMouseClicked(e -> {
            propertyChange = false;
            chosenField = "description";
        });
        name.setOnMouseClicked(e -> {
            propertyChange = false;
            chosenField = "name";
        });
        subCategory.setOnMouseClicked(e -> {
            propertyChange = false;
            chosenField = "sub-category";
        });
    }


    private void setError(String error) {
        this.error.setText(error);
        this.error.setVisible(true);
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

    @FXML
    private void edit() {
        if (chosenField == null || chosenField.isEmpty()) {
            setError("Please chose a field first!!");
            return;
        }
        if (propertyChange) {
            SpecialProperty property = new SpecialProperty(chosenField);
            int index = product.getSpecialProperties().indexOf(property);
            property.setType(product.getSpecialProperties().get(index).getType());
            property.setUnit(product.getSpecialProperties().get(index).getUnit());
            if (product.getSpecialProperties().get(index).getType().equals("numeric")) {
                try {
                    property.setNumericValue(Double.parseDouble(editValue.getText()));
                } catch (NumberFormatException e) {
                    setError("Please enter an integer for this field");
                    return;
                }
            } else {
                property.setValue(editValue.getText());
            }
            edit("property", property);
        } else {
            edit(chosenField, new SpecialProperty(""));
        }
    }

    private void edit(String field, SpecialProperty property) {
        ClientMessage request = new ClientMessage("edit product");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("product id", product.getId());
        reqInfo.put("field", field);
        reqInfo.put("new value", editValue.getText());
        reqInfo.put("change type", "");
        request.setHashMap(reqInfo);
        request.setProperty(property);
        processAnswer(send(request));
    }

    private void processAnswer(ServerMessage answer) {
        if (answer.getType().equals("Error")) {
            setError(answer.getErrorMessage());
        } else {
            update();
        }
    }
}
