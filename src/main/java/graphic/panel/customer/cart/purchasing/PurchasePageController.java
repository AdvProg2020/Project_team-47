package graphic.panel.customer.cart.purchasing;

import graphic.GraphicView;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import graphic.panel.customer.cart.CustomerCartController;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchasePageController extends PageController {
    private static PageController controller;
    public TextField firstName;
    public TextField lastName;
    public TextArea address;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new PurchasePageController();
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

    }

    public void confirm(MouseEvent mouseEvent) {
        //todo amir setting properties and sending message
        //ClientMessage request = new ClientMessage();
        ServerMessage answer = new ServerMessage();
        answer.setType("salam");
        //answer = send(request);
        if (answer.getType().equals("Successful") || true) {
            showDiscountCodeAlert();
        }

    }

    private void showDiscountCodeAlert() {
        Optional<String> inputUsername = GraphicView.showAlertPage("enter discountCode", "code");

        if (inputUsername.get().equals("")) {
            // todo amir no discount code
        } else {

        }
        finishShopping();
    }

    private void finishShopping() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Purchase Confirmation");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            //todo amir finishing purchase

            GraphicView.getInstance().changeScene(MainMenuPage.getInstance());
        }
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
