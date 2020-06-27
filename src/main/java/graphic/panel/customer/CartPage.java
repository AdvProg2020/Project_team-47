package graphic.panel.customer;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.ProductPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.send.receive.CartInfo;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CartPage extends PageController {
    @FXML
    private
    Label priceLabel;
    @FXML
    private VBox vBox;
    @FXML
    private TextField productId;
    @FXML
    private TextField seller;


    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        ClientMessage request = new ClientMessage("show products in cart");
        CartInfo cartInfo = send(request).getCartInfo();
        priceLabel.setText("Total Price: " + cartInfo.getPrice());
        initializeProducts(cartInfo);
    }

    private void initializeProducts(CartInfo cartInfo) {
        vBox.getChildren().clear();
        for (CartInfo.ProductInCart product : cartInfo.getProducts()) {
            try {
                ProductPane.setCart(product.getProduct(), product.getSeller(), product.getNumberInCart());
                vBox.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/panel/ProductPane.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    @FXML
    private void increase() {
        sendChangeProduct("increase product in cart");
    }

    private void sendChangeProduct(String type) {
        ClientMessage request = new ClientMessage(type);
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("product id", productId.getText());
        reqInfo.put("seller username", seller.getText());
        request.setHashMap(reqInfo);
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            showAlert(answer.getErrorMessage());
        } else {
            update();
        }
    }

    @FXML
    private void decrease() {
        sendChangeProduct("decrease product in cart");
    }

    @FXML
    private void purchase() throws IOException {
        if (!GraphicView.getInstance().isLoggedIn()) {
            showAlert("Please login or register first!!");
        } else {
            if (sendAndProcess(new ClientMessage("purchase cart")))
                TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/panel/customer/PurchasePage.fxml")));
        }


    }

    private boolean sendAndProcess(ClientMessage request) {
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(answer.getErrorMessage());
            alert.showAndWait();
            return false;
        } else return true;
    }

    private void showAlert(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }
}
