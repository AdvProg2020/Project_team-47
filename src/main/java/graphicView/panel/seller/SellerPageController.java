package graphicView.panel.seller;

import graphicView.GraphicView;
import graphicView.PageController;
import graphicView.panel.seller.log.LogPage;
import graphicView.panel.seller.offs.ManageOffsPage;
import javafx.fxml.FXML;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerPageController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        return controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
    }

    @FXML
    private void logOut() {
        ClientMessage request = new ClientMessage("logout");
        ServerMessage answer = send(request);
        if(answer.getType().equals("Successful"))
            GraphicView.getInstance().goToFirstPage();
    }

    @FXML
    private void accountInfo() {
        // TODO: 6/12/2020
    }

    @FXML
    private void manageOffs() {
        GraphicView.getInstance().changeScene(ManageOffsPage.getInstance());
    }

    @FXML
    private void manageProducts() {
        // TODO: 6/12/2020  
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
        GraphicView.getInstance().changeScene(LogPage.getInstance());
    }

    @Override
    public void clearPage() {}

    @Override
    public void update() {}

}
