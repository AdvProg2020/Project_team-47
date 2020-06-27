package graphic.panel.customer;

import graphic.GraphicView;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import graphic.panel.AccountPage;
import graphic.panel.customer.CustomerDiscountCodes.CustomerDiscountCodesController;
import graphic.panel.customer.CustomerDiscountCodes.CustomerDiscountCodesPage;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryPage;
import graphic.panel.customer.cart.CustomerCartController;
import graphic.panel.customer.cart.CustomerCartPage;
import javafx.scene.input.MouseEvent;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPageController extends PageController {
    private static PageController controller;
    public boolean bol = false;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerPageController();
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

    public void showPersonalInfo() {
        GraphicView.getInstance().changeScene(AccountPage.getScene());
    }

    public void goToShoppingCart() {
        //getting cart info
        ClientMessage request = new ClientMessage("show products in cart");
        ServerMessage answer = send(request);

        if (answer.getType().equals("Successful")) {
            CustomerCartController.cartInfo = answer.getCartInfo();
            GraphicView.getInstance().changeSceneWithoutUpdate(CustomerCartPage.getInstance());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }

    }

    public void showPurchaseHistory(MouseEvent mouseEvent) {
        //getting purchase history
        ClientMessage request = new ClientMessage("view orders");

        ServerMessage answer = send(request);

        if (answer.getType().equals("Successful")) {
            CustomerPurchaseHistoryController.purchaseHistoryArrayList = answer.getLogInfoArrayList();
            GraphicView.getInstance().changeScene(CustomerPurchaseHistoryPage.getInstance());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }

    }

    public void showDiscountCodes(MouseEvent mouseEvent) {
        //getting discount codes
        ClientMessage request = new ClientMessage("view discount codes customer");

        ServerMessage answer = send(request);

        if (answer.getType().equals("Successful")) {
            CustomerDiscountCodesController.discountCodes = answer.getDiscountCodeInfoArrayList();
            GraphicView.getInstance().changeScene(CustomerDiscountCodesPage.getInstance());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
    }

    public void logout(MouseEvent mouseEvent) {
        ClientMessage request = new ClientMessage("logout");
        ServerMessage answer = send(request);
        if (answer.getType().equals("Successful")) {
            GraphicView.getInstance().setLoggedIn(false);
            GraphicView.getInstance().changeScene(MainMenuPage.getInstance());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
    }
}
