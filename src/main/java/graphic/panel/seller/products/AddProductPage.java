package graphic.panel.seller.products;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductPage extends PageController {
    private static PageController controller;
    private Image image;
    private ArrayList<SpecialProperty> specialProperties;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane propertiesAnchorPane;
    @FXML
    private TextField id;
    @FXML
    private TextField priceAddingSeller;
    @FXML
    private TextField numberInStockAddingSeller;
    @FXML
    private TextField productName;
    @FXML
    private TextField price;
    @FXML
    private TextField numberInStock;
    @FXML
    private TextField category;
    @FXML
    private TextField subCategory;
    @FXML
    private TextField company;
    @FXML
    private TextArea description;
    @FXML
    private Text addSellerError;
    @FXML
    private Text goToPropertyError;
    @FXML
    private Text addProductError;
    @FXML
    private VBox propertiesVBox;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/products/add/AddProductPage.fxml");
    }

    @Override
    public void clearPage() {
        id.setText("");
        priceAddingSeller.setText("");
        numberInStockAddingSeller.setText("");
        productName.setText("");
        price.setText("");
        numberInStock.setText("");
        category.setText("");
        subCategory.setText("");
        company.setText("");
        description.setText("");
    }

    @Override
    public void update() {
        addSellerError.setVisible(false);
        goToPropertyError.setVisible(false);
        addProductError.setVisible(false);
        image = null;
        clearPage();
        backFromProperties();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

    @FXML
    private void addToSeller() {
        if (id.getText().isEmpty()) {
            setAddToSellerError("Please enter an id!!");
            return;
        }
        if (priceAddingSeller.getText().isEmpty()) {
            setAddToSellerError("Please enter a price!!");
            return;
        } else {
            try {
                Double.parseDouble(priceAddingSeller.getText());
            } catch (NumberFormatException e) {
                setAddToSellerError("Please enter valid price!!");
                return;
            }
        }
        if (numberInStockAddingSeller.getText().isEmpty()) {
            setAddToSellerError("Please enter number in stock!!");
            return;
        } else {
            try {
                Integer.parseInt(numberInStockAddingSeller.getText());
            } catch (NumberFormatException e) {
                setAddToSellerError("Please enter valid number in stock!!");
                return;
            }
        }
        addToSeller(id.getText(),priceAddingSeller.getText(),numberInStockAddingSeller.getText());
    }

    private void setAddToSellerError(String error) {
        addSellerError.setText(error);
        addSellerError.setVisible(true);
    }

    private void addToSeller(String id, String price, String numberInStock) {
        ClientMessage request = new ClientMessage("add to seller");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("product id", id);
        reqInfo.put("price", price);
        reqInfo.put("number in stock", numberInStock);
        request.setHashMap(reqInfo);
        ServerMessage answer = send(request);
        if (answer.getType().equalsIgnoreCase("Error")) {
            addSellerError.setText(answer.getErrorMessage());
            addSellerError.setVisible(true);
        } else{ update();}
    }

    @FXML
    private void choosePic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg"));
        File imageFile = fileChooser.showOpenDialog(GraphicView.getInstance().getWindow());
        try {
            image = new Image(imageFile.toURI().toString());
        } catch (Exception e) {
            image = null;
        }
    }

    @FXML
    private void setProperties() {
        if (image == null) setGoToPropertyError("Please choose an picture");
        else if (productName.getText().isEmpty()) setGoToPropertyError("Please enter a name!!");
        else if(!doubleIsValid(price.getText())) setGoToPropertyError("Please enter valid price!!");
        else if(!integerIsValid(numberInStock.getText())) setGoToPropertyError("Please enter valid number in stock!!");
        else if(category.getText().isEmpty()) setGoToPropertyError("Please enter a category name!!");
        else if(subCategory.getText().isEmpty()) setGoToPropertyError("Please enter a sub category name!!");
        else if (company.getText().isEmpty()) setGoToPropertyError("Please enter a company name!!");
        else if (description.getText().isEmpty()) setGoToPropertyError("Please enter description!!");
        else if (categoryIsValid(subCategory.getText())) goToPropertiesPage();
    }

    private void goToPropertiesPage() {
        mainAnchorPane.setVisible(false);
        propertiesAnchorPane.setVisible(true);
        addProductError.setVisible(false);
        propertiesVBox.getChildren().clear();
        for (SpecialProperty specialProperty : specialProperties) {
            HBox propertyHBox = new HBox(10);
            propertyHBox.setAlignment(Pos.CENTER);
            Label propertyNameLabel = new Label(specialProperty.getKey());
            propertyNameLabel.setFont(new Font(15));
            TextField propertyTextField = new TextField();
            propertyTextField.setFont(new Font(15));
            propertyTextField.setPromptText(specialProperty.getType());
            propertyHBox.getChildren().addAll(propertyNameLabel, propertyTextField);
            if (specialProperty.getType().equalsIgnoreCase("numeric")) {
                Label unitLabel = new Label(specialProperty.getUnit());
                unitLabel.setFont(new Font(15));
                propertyHBox.getChildren().add(unitLabel);
            }
            propertiesVBox.getChildren().add(propertyHBox);
        }
    }

    @FXML
    private void backFromProperties() {
        mainAnchorPane.setVisible(true);
        propertiesAnchorPane.setVisible(false);
    }

    @FXML
    private void addProduct() {
        if (!propertiesIsValid()) {
            setAddProductError("Please enter valid properties value!!");
            return;
        }
        ClientMessage request = new ClientMessage("add product");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("name", productName.getText());
        reqInfo.put("company", company.getText());
        reqInfo.put("category", category.getText());
        reqInfo.put("sub-category", subCategory.getText());
        reqInfo.put("description", description.getText());
        reqInfo.put("number-in-stock", numberInStock.getText());
        reqInfo.put("price", price.getText());
        request.setHashMap(reqInfo);
        request.setFile(PageController.imageToByte(image));
        request.setFileExtension(".jpg");
        request.setProperties(getProperties());
        sendReq(request);
    }

    private void sendReq(ClientMessage request) {
        ServerMessage answer = send(request);
        if (answer.getType().equalsIgnoreCase("Error")) {
            setAddProductError(answer.getErrorMessage());
        } else {
            update();
        }
    }

    private ArrayList<SpecialProperty> getProperties() {
        ArrayList<SpecialProperty> properties = new ArrayList<>(specialProperties);
        for (int i = 0; i < propertiesVBox.getChildren().size(); i++) {
            if ("numeric".equalsIgnoreCase(getPropertyType((HBox) propertiesVBox.getChildren().get(i)))) {
                try {
                    properties.get(i).setNumericValue(Double.parseDouble(
                            Objects.requireNonNull(getPropertyValue((HBox) propertiesVBox.getChildren().get(i)))));
                } catch (NumberFormatException | NullPointerException ignored) {
                }
            } else {
                properties.get(i).setValue(getPropertyValue((HBox) propertiesVBox.getChildren().get(i)));
            }
        }
        return properties;
    }

    private String getPropertyValue(HBox hBox) {
        for (Node child : hBox.getChildren()) {
            if(child instanceof TextField) return ((TextField) child).getText();
        }
        return null;
    }

    private String getPropertyType(HBox hBox) {
        for (Node child : hBox.getChildren()) {
            if(child instanceof TextField) return ((TextField)child).getPromptText();
        }
        return null;
    }

    private boolean propertiesIsValid() {
        for (Node child : propertiesVBox.getChildren()) {
            if(child instanceof HBox && !propertyIsValid((HBox)child)) return false;
        }
        return true;
    }

    private boolean propertyIsValid(HBox node) {
        for (Node  child: node.getChildren()) {
            if (child instanceof TextField) {
                TextField temp = (TextField) child;
                if(temp.getText().isEmpty()) return false;
                if ( temp.getPromptText().equalsIgnoreCase("numeric") && !doubleIsValid(temp.getText()))
                    return false;
            }
        }
        return true;
    }

    private boolean categoryIsValid(String subCategory) {
        ClientMessage request = new ClientMessage("show sub category info");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("main category", category.getText());
        reqInfo.put("sub category", subCategory);
        request.setHashMap(reqInfo);
        ServerMessage answer = send(request);
        if (answer.getType().equalsIgnoreCase("Error")) {
            setGoToPropertyError(answer.getErrorMessage());
            return false;
        } else if (!answer.getCategoryInfo().getMainCategory().equalsIgnoreCase(category.getText())) {
            setGoToPropertyError("This category hasn't this sub category!!");
            return false;
        }
        specialProperties = answer.getCategoryInfo().getSpecialProperties();
        return true;
    }

    private boolean integerIsValid(String number) {
        if(number.isEmpty()) return false;
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean doubleIsValid(String number) {
        if(number.isEmpty()) return false;
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void setGoToPropertyError(String error) {
        goToPropertyError.setText(error);
        goToPropertyError.setVisible(true);
    }

    private void setAddProductError(String error) {
        addProductError.setText(error);
        addProductError.setVisible(true);
    }
}
