package graphic.registerAndLoginMenu.registerMenu.customer;

import graphic.GraphicView;
import graphic.PageController;
import graphic.registerAndLoginMenu.registerAndLogin.RegisterAndLoginPage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class RegisterCustomerController extends PageController {

    private static PageController controller;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputFirstName;
    public TextField inputLastName;
    public TextField inputEmail;

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

    public void registerCustomer() {
        ClientMessage request = new ClientMessage("register");
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("username", inputUsername.getText());
        hashMap.put("password", inputPassword.getText());
        hashMap.put("first-name", inputFirstName.getText());
        hashMap.put("last-name", inputLastName.getText());
        hashMap.put("email", inputEmail.getText());

        request.setHashMap(hashMap);
        ServerMessage answer = send(request);

        if (answer.getType().equals("Successful")) {
            GraphicView.getInstance().changeScene(RegisterAndLoginPage.getNextPageForCustomer());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

}
