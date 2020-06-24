package graphic.registerAndLoginMenu.registerMenu.seller;

import graphic.GraphicView;
import graphic.PageController;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterSellerController extends PageController {

    private static PageController controller;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputFirstName;
    public TextField inputLastName;
    public TextField inputEmail;
    public TextField inputCompanyName;
    public TextField inputCompanyInfo;


    public static PageController getInstance() {
        if (controller == null) {
            controller = new RegisterSellerController();
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

    public void registerSeller(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
