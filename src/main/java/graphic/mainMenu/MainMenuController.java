package graphic.mainMenu;

import graphic.GraphicView;
import graphic.PageController;
import graphic.productsMenu.ProductsMenuPage;
import graphic.registerAndLoginMenu.registerAndLogin.RegisterAndLoginPage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController extends PageController {

    private static PageController controller;

    @FXML
    private Label errorLabel;

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

    }


    @FXML
    private void showTextFields() {

    }

    @FXML
    private void hideTextFields() {

    }

    private FadeTransition[] getFadingAnimation() {

        FadeTransition[] fadeTransitions = new FadeTransition[5];
        /*
        fadeTransitions[0] = new FadeTransition(Duration.millis(600), username);
        fadeTransitions[1] = new FadeTransition(Duration.millis(600), turnLimit);
        fadeTransitions[2] = new FadeTransition(Duration.millis(600), undoLimit);
        fadeTransitions[3] = new FadeTransition(Duration.millis(600), time);
        fadeTransitions[4] = new FadeTransition(Duration.millis(600), errorLabel);
        */
        return fadeTransitions;
    }

    private void playFadeAnimation(FadeTransition[] fadeTransitions) {
        /*
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.play();
        }
        */
    }

    private void createFadeAnimations(double from, double to) {
        FadeTransition[] fadeTransitions = getFadingAnimation();
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.setFromValue(from);
            fadeTransition.setToValue(to);
        }
        playFadeAnimation(fadeTransitions);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void clearPage() {
        errorLabel.setVisible(false);

    }

    @Override
    public void update() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}