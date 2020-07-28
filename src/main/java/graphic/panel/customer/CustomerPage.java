package graphic.panel.customer;

import controller.Controller;
import graphic.MainFX;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.AccountPage;
import graphic.panel.seller.log.LogsPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPage extends PageController {
    private static PageController controller;
    @FXML
    public TextField raiseMoney;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerPage();
        }
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/customer/CustomerPage.fxml");
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logout() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("logout");
        send(request);
        MainFX.getInstance().setLoggedIn(false);
        MainFX.getInstance().goToFirstPage();
    }

    public void personalInfo() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(AccountPage.getScene());
    }

    public void shoppingCart() throws IOException {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/panel/customer/CartPage.fxml")));
    }

    public void purchaseHistory() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(LogsPage.getScene());
    }

    public void discountCodes() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(DiscountPage.getScene());
    }

    public void showProducts() throws IOException {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Products.fxml")));
    }

    public void raiseBalance() {
        ClientMessage clientMessage = new ClientMessage("raise_balance " + raiseMoney.getText());
        ServerMessage serverMessage = send(clientMessage);
        //todo amir
    }
}
