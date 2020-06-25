package graphic.panel.customer.CustomerPurchaseHistory;

import graphic.GraphicView;
import graphic.PageController;
import graphic.mainMenu.MainMenuController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryPage;
import graphic.productsMenu.ProductsMenuPage;
import graphic.registerAndLoginMenu.registerAndLogin.RegisterAndLoginPage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPurchaseHistoryController extends PageController {
    private static PageController controller;
    public TableView tableView;
    public TableColumn username;
    public TableColumn score;
    public Button backButton;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerPurchaseHistoryController();
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

    public void showPersonalInfo(MouseEvent mouseEvent) {
    }

    public void goToShoppingCart(MouseEvent mouseEvent) {
    }

    public void showPurchaseHistory(MouseEvent mouseEvent) {
        //get purchase history
        //goto purchase history

        GraphicView.getInstance().changeScene(CustomerPurchaseHistoryPage.getInstance());

    }

    public void showDiscountCodes(MouseEvent mouseEvent) {
    }

    public void logout(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
