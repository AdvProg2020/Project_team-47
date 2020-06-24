package graphic.registerAndLoginMenu.registerMenu.customer;

import com.sun.tools.javac.Main;
import graphic.GraphicView;
import graphic.PageController;
import graphic.productsMenu.ProductsMenuPage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;




public class RegisterCustomerController extends PageController {

    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputFirstName;
    public TextField inputLastName;
    public TextField inputEmail;

    private static PageController controller;


    public static PageController getInstance() {
        if (controller == null) {
            controller = new RegisterCustomerController();
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

    public void registerCustomer(MouseEvent mouseEvent) {
        ClientMessage request = new ClientMessage("register");

        ServerMessage answer = send(request);
        if(answer.getType().equals("Successful"))
            GraphicView.getInstance().changeScene(ProductsMenuPage.getInstance());
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

}
