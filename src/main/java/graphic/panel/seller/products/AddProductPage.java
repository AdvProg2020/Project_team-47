package graphic.panel.seller.products;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddProductPage extends PageController {
    private static PageController controller;
    private Image image;
    private ArrayList<SpecialProperty> specialProperties;
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
    private Text addProductError;

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
        addProductError.setVisible(false);
        image = null;
        clearPage();
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
        if (price.getText().isEmpty()) {
            setAddToSellerError("Please enter a price!!");
            return;
        } else {
            try {
                Double.parseDouble(price.getText());
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
        addToSeller(id.getText(),price.getText(),numberInStockAddingSeller.getText());
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", ".jpg"));
        File imageFile = fileChooser.showOpenDialog(GraphicView.getInstance().getWindow());
        try {
            image = new Image(imageFile.toURI().toString());
        } catch (Exception e) {
            image = null;
        }
    }

    @FXML
    private void setProperties() {
        if (image == null) setAddProductError("Please choose an picture");
        else if (productName.getText().isEmpty()) setAddProductError("Please enter a name!!");
        else if(!doubleIsValid(price.getText())) setAddProductError("Please enter valid price!!");
        else if(!integerIsValid(numberInStock.getText()))setAddProductError("Please enter valid number in stock!!");
        else if(category.getText().isEmpty()) setAddProductError("Please enter a category name!!");
        else if(subCategory.getText().isEmpty()) setAddProductError("Please enter a sub category name!!");
        else if (company.getText().isEmpty()) setAddProductError("Please enter a company name!!");
        else if(description.getText().isEmpty()) setAddProductError("Please enter description!!");


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

    private void setAddProductError(String error) {
        addProductError.setText(error);
        addProductError.setVisible(true);
    }
}
