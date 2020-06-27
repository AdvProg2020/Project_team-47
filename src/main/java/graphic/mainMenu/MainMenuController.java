package graphic.mainMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.offsMenu.OffsMenuController;
import graphic.panel.customer.CustomerPage;
import graphic.productsMenu.ProductsMenuPage;
import graphic.registerAndLoginMenu.RegisterPage;
import graphic.registerAndLoginMenu.loginMenu.LoginMenuController;
import graphic.registerAndLoginMenu.registerAndLogin.RegisterAndLoginPage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController extends PageController {
    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new MainMenuController();
        }
        return controller;
    }


    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void loginAndRegister() {
        GraphicView.getInstance().changeScene(RegisterAndLoginPage.getInstance(ProductsMenuPage.getInstance(), ProductsMenuPage.getInstance()));
    }

    @FXML
    private void products() {
        GraphicView.getInstance().changeScene(ProductsMenuPage.getInstance());
    }

    @FXML
    private void offs() {
        ClientMessage request = new ClientMessage("initialize all offs page");
        ServerMessage answer = send(request);

        if (answer.getType().equals("Successful")) {
            OffsMenuController.offs = answer.getOffInfoArrayList();
            GraphicView.getInstance().changeScene(OffsMenuController.getScene());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
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

    public void login() {
        GraphicView.getInstance().changeScene(LoginMenuController.getScene());
    }

    public void register() {
        GraphicView.getInstance().changeScene(RegisterPage.getScene());
    }
}