package graphic.panel.customer.cart;

import graphic.GraphicView;
import graphic.PageController;
import graphic.panel.customer.CustomerPageController;
import graphic.panel.customer.cart.purchasing.PurchasePage;
import graphic.productMenu.ProductPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.send.receive.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerCartController extends PageController {
    private static PageController controller;

    public static ArrayList<CartInfo.ProductInCart> productsInCart;
    public static CartInfo cartInfo;

    @FXML
    public TextField totalPriceOfCart;

    @FXML
    TableView<CartTable> tableView;

    @FXML
    TableColumn<CartTable, String> name;

    @FXML
    TableColumn<CartTable, Double> price;

    @FXML
    TableColumn<CartTable, Integer> count;

    @FXML
    TableColumn<CartTable, Double> totalPrice;

    @FXML
    TableColumn<CartTable, String> sellerId;


    ObservableList<CartTable> data = FXCollections.observableArrayList();



    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerCartController();
        }
        return controller;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        ClientMessage request = new ClientMessage("show products in cart");
        ServerMessage answer = send(request);

        if(answer.getType().equals("Successful")){
            //CustomerCartController.cartInfo = answer.getCartInfo();
            setFields();
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFields();
    }

    private void setFields() {
        setData();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        tableView.setItems(data);
    }

    private void setData() {
        productsInCart = cartInfo.getProducts();
        for (CartInfo.ProductInCart productInCart : CustomerCartController.productsInCart) {
            ProductInfo productInfo = productInCart.getProduct();
            double totalPrice = productInCart.getPrice() * productInCart.getNumberInCart();
            data.add(new CartTable(productInfo.getName(), productInCart.getPrice(), productInCart.getNumberInCart(), totalPrice, productInCart.getSeller()));
        }

        totalPriceOfCart.setText("total price of cart   : " + cartInfo.getPrice());
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

    public void showProduct(MouseEvent mouseEvent) {
        CartTable selectedTable;
        selectedTable = tableView.getSelectionModel().getSelectedItem();
        ClientMessage clientMessage = new ClientMessage("view product in cart");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("product id", selectedTable.getName());
        clientMessage.setHashMap(hashMap);
        ServerMessage answer = send(clientMessage);
        if (answer.getType().equals("Successful")) {
            GraphicView.getInstance().changeScene(ProductPage.getInstance());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
    }

    public void increase(MouseEvent mouseEvent) {
        increaseAndDecrease("increase product in cart");
    }

    public void decrease(MouseEvent mouseEvent) {
        increaseAndDecrease("decrease product in cart");
    }

    private void increaseAndDecrease(String clientMessageText) {
        CartTable selectedTable;
        selectedTable = tableView.getSelectionModel().getSelectedItem();
        ClientMessage clientMessage = new ClientMessage(clientMessageText);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("product id", selectedTable.getName());
        hashMap.put("seller username", selectedTable.getSellerId());

        clientMessage.setHashMap(hashMap);

        ServerMessage answer = send(clientMessage);

        if (answer.getType().equals("Successful")) {
            update();
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
    }

    public void buyCart(MouseEvent mouseEvent) {
        GraphicView.getInstance().changeScene(PurchasePage.getInstance());
    }
}
