package graphic.registerAndLoginMenu.loginMenu;

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

public class LoginMenuController extends PageController {
    private static PageController controller;
    public TextField username;
    public TextField password;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new LoginMenuController();
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

    public void showTextFields(MouseEvent mouseEvent) {
    }

    public void hideTextFields(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

    public void login(MouseEvent mouseEvent) {
        ClientMessage clientMessage = new ClientMessage("login");

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("username", username.getText());
        hashMap.put("password", password.getText());

        clientMessage.setHashMap(hashMap);
        ServerMessage answer = send(clientMessage);
        if(answer.getType().equals("Successful")){
            GraphicView.getInstance().changeScene(RegisterAndLoginPage.getNextPageForCustomer());
        } else {
            GraphicView.getInstance().showErrorAlert(answer.getErrorMessage());
        }
        System.out.println("salam");
    }
}
