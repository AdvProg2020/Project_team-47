package graphic.panel.customer;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.AccountPage;
import graphic.panel.seller.log.LogsPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import model.send.receive.ClientMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPage extends PageController {
    private static PageController controller;

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
        ClientMessage request = new ClientMessage("logout");
        send(request);
        GraphicView.getInstance().setLoggedIn(false);
        GraphicView.getInstance().goToFirstPage();
    }

    public void personalInfo() {
        GraphicView.getInstance().changeScene(AccountPage.getScene());
    }

    public void shoppingCart() throws IOException {
        GraphicView.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/panel/customer/CartPage.fxml")));
    }

    public void purchaseHistory() {
        GraphicView.getInstance().changeScene(LogsPage.getScene());
    }

    public void discountCodes() {
        GraphicView.getInstance().changeScene(DiscountPage.getScene());
    }

    public void showProducts() throws IOException {
        GraphicView.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Products.fxml")));
    }

}
