package graphic.registerAndLoginMenu.registerMenu.seller;

import graphic.GraphicView;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import graphic.productsMenu.ProductsMenuPage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
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
        ClientMessage request = new ClientMessage("register");
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("username", inputUsername.getText());
        hashMap.put("password", inputPassword.getText());
        hashMap.put("last-name", inputLastName.getText());
        hashMap.put("first-name", inputFirstName.getText());
        hashMap.put("email", inputEmail.getText());
        hashMap.put("company-name", inputCompanyName.getText());
        hashMap.put("company-info", inputCompanyInfo.getText());

        request.setHashMap(hashMap);

        ServerMessage answer = send(request);
        if(answer.getType().equals("Successful")){
            GraphicView.getInstance().changeScene(MainMenuPage.getInstance());
        } else {
            //todo amir
            GraphicView.getInstance().changeScene(MainMenuPage.getInstance());
        }
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }
}
