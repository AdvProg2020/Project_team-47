package graphic.panel.customer;

import graphic.PageController;
import javafx.scene.Scene;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionsPage extends PageController {

    private static PageController controller;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/auction/AuctionsPage.fxml");
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

    public void fresh() {
        ClientMessage request = new ClientMessage("give auctions");
        ServerMessage answer = send(request);
    }
}
