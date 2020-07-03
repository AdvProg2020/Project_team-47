package graphic.panel.manager;

import graphic.MainFX;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.AccountPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManagerPage extends PageController {

    @FXML
    private ImageView avatar;
    @FXML
    private TextField productId;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/ManagerPage.fxml");
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


    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatar.setImage(MainFX.getInstance().getAvatar());
    }

    @FXML
    private void manageDiscounts() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(ManageDiscountPage.getScene());
    }

    @FXML
    private void manageCategories() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(ManageCategoriesPage.getScene());
    }

    @FXML
    private void manageUsers() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(ManageUsersPage.getScene());
    }

    @FXML
    private void productsPage() throws IOException {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Products.fxml")));
    }

    @FXML
    private void manageRequests() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(ManageRequestPage.getScene());
    }

    public void removeProduct() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("remove product manager");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("id", productId.getText());
        request.setHashMap(reqInfo);
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(answer.getErrorMessage());
            alert.showAndWait();
        } else {
            productId.setText("");
        }
    }
}
