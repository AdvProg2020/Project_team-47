package graphic.panel.seller;

import graphic.GraphicView;
import graphic.PageController;
import graphic.panel.AccountPage;
import graphic.panel.seller.log.LogsPage;
import graphic.panel.seller.offs.ManageOffsPage;
import graphic.panel.seller.products.ManageProductsPage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerPage extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/SellerPage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
    }

    @FXML
    private void logOut() {
        ClientMessage request = new ClientMessage("logout");
        ServerMessage answer = send(request);
        if (answer.getType().equals("Successful")) {
            GraphicView.getInstance().setLoggedIn(false);
            GraphicView.getInstance().goToFirstPage();
        }
    }

    @FXML
    private void accountInfo() {
        GraphicView.getInstance().changeScene(AccountPage.getScene());
    }

    @FXML
    private void manageOffs() {
        GraphicView.getInstance().changeScene(ManageOffsPage.getScene());
    }

    @FXML
    private void manageProducts() {
        GraphicView.getInstance().changeScene(ManageProductsPage.getScene());
    }

    @FXML
    private void productsPage() {
        // TODO: 6/12/2020  
    }

    @FXML
    private void offsPage() {
        // TODO: 6/12/2020  
    }

    @FXML
    private void buyLogs() {
        GraphicView.getInstance().changeScene(LogsPage.getScene());
    }

    @Override
    public void clearPage() {
    }

    @Override
    public void update() {
    }

}
