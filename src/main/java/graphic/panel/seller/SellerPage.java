package graphic.panel.seller;

import graphic.MainFX;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.AccountPage;
import graphic.panel.seller.log.LogsPage;
import graphic.panel.seller.offs.ManageOffsPage;
import graphic.panel.seller.products.ManageProductsPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerPage extends PageController {
    private static PageController controller;
    @FXML
    public TextField raiseMoney;
    @FXML
    private ImageView avatar;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/SellerPage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        avatar.setImage(MainFX.getInstance().getAvatar());
        update();
    }

    @FXML
    private void logOut() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("logout");
        ServerMessage answer = send(request);
        if (answer.getType().equals("Successful")) {
            MainFX.getInstance().setLoggedIn(false);
            MainFX.getInstance().goToFirstPage();
        }
    }

    @FXML
    private void accountInfo() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(AccountPage.getScene());
    }

    @FXML
    private void manageOffs() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(ManageOffsPage.getScene());
    }

    @FXML
    private void manageProducts() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(ManageProductsPage.getScene());
    }

    @FXML
    private void productsPage() throws IOException {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Products.fxml")));
    }

    @FXML
    private void buyLogs() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(LogsPage.getScene());
    }

    @Override
    public void clearPage() {
    }

    @Override
    public void update() {
    }

    public void lowerWalletMoney(ActionEvent actionEvent) {
        ClientMessage clientMessage = new ClientMessage("lower_wallet_money " + raiseMoney.getText());
        System.out.println("client message created");
        ServerMessage serverMessage = send(clientMessage);
        if (serverMessage.getType().equals("Successful")) {
            raiseMoney.setText("");
        } else {
            raiseMoney.setText("error");
        }
    }
}
