package graphic.panel.customer.cart;

import graphic.GraphicView;
import graphic.Page;
import graphic.PageController;
import graphic.panel.customer.CustomerDiscountCodes.CustomerDiscountCodesController;
import graphic.panel.customer.CustomerDiscountCodes.DiscountCodesTable;
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
import javafx.scene.text.Text;
import model.others.Product;
import model.others.ShoppingCart;
import model.send.receive.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        tableView.setItems(data);
    }

    private void setData() {

        for (CartInfo.ProductInCart productInCart : CustomerCartController.productsInCart) {
            ProductInfo productInfo = productInCart.getProduct();
            double totalPrice = productInCart.getPrice() * productInCart.getNumberInCart();
            data.add(new CartTable(productInfo.getName(), productInCart.getPrice(), productInCart.getNumberInCart(), totalPrice));
        }
        totalPriceOfCart.setText("total price of cart   : " + cartInfo.getPrice());
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

    public void showProduct(MouseEvent mouseEvent) {
        CartTable selectedTable;
        selectedTable = tableView.getSelectionModel().getSelectedItem();
        //todo amir send message to controller

        //going to product page
        GraphicView.getInstance().changeScene(ProductPage.getInstance());

    }

    public void increase(MouseEvent mouseEvent) {
        CartTable selectedTable;
        selectedTable = tableView.getSelectionModel().getSelectedItem();
        ClientMessage clientMessage = new ClientMessage("increase product in cart");
        // todo amir setting properties
        ServerMessage answer = send(clientMessage);
        ((CustomerPageController)CustomerPageController.getInstance()).bol = true;
        if (answer.getType().equals("Successful") || true) {

            //((CustomerPageController)CustomerPageController.getInstance()).goToShoppingCart();
            // todo amir refreshing page
            update();


        }
    }

    public void decrease(MouseEvent mouseEvent) {
        CartTable selectedTable;
        selectedTable = tableView.getSelectionModel().getSelectedItem();
        ClientMessage clientMessage = new ClientMessage("decrease product in cart");
        // todo amir setting properties
        ServerMessage answer = send(clientMessage);

        if (answer.getType().equals("Successful") || true) {
            // todo amir refreshing page
        }
    }

    public void buyCart(MouseEvent mouseEvent) {
        // todo amir sending message to server
        GraphicView.getInstance().changeScene(PurchasePage.getInstance());
    }
}
