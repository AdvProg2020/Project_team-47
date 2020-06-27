package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.panel.AccountPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManagerPage extends PageController {

    @FXML private TextField productId;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/ManagerPage.fxml");
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


    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void manageDiscounts() {
        GraphicView.getInstance().changeScene(ManageDiscountPage.getScene());
    }

    @FXML
    private void manageCategories() {
        GraphicView.getInstance().changeScene(ManageCategoriesPage.getScene());
    }

    @FXML
    private void manageUsers() {
        GraphicView.getInstance().changeScene(ManageUsersPage.getScene());
    }

    @FXML
    private void productsPage() throws IOException {
        GraphicView.getInstance().changeScene(TemplatePage.getScene());
        TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Products.fxml")));
    }

    @FXML
    private void manageRequests() {
        GraphicView.getInstance().changeScene(ManageRequestPage.getScene());
    }

    public void removeProduct() {
        ClientMessage request = new ClientMessage("remove product manager");
        HashMap<String,String> reqInfo = new HashMap<>();
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
