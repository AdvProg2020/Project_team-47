package graphicView.mainMenu;

import graphicView.GraphicView;
import graphicView.PageController;
import graphicView.registerAndLoginMenu.registerAndLogin.RegisterAndLoginPage;
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
        GraphicView.getInstance().changeScene(RegisterAndLoginPage.getInstance());
    }

    @FXML
    private void products() {

    }

    @FXML
    private void offs() {

    }


    @FXML
    private void showTextFields() {
        /*
        //this function will use to show fields when user move mouse to new game button
        if (username.isVisible())
            return;
        Thread thread = new Thread(() -> {
            username.setVisible(true);
            turnLimit.setVisible(true);
            undoLimit.setVisible(true);
            time.setVisible(true);
            createFadeAnimations(0, 1);
        });
        thread.start();
*/
    }

    @FXML
    private void hideTextFields() {
        /*
        //this function will use to hide new game fields when user move mouse to other buttons except
        //new game button
        if (!username.isVisible())
            return;
        Thread thread = new Thread(() -> {
            createFadeAnimations(1, 0);
            username.setVisible(false);
            turnLimit.setVisible(false);
            undoLimit.setVisible(false);
            time.setVisible(false);
        });
        thread.start();
        */
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
        /*username.setVisible(false);
        username.setOpacity(0);
        username.setText("");
        undoLimit.setText("");
        time.setText("");
        time.setVisible(false);
        time.setOpacity(0);
        undoLimit.setVisible(false);
        undoLimit.setOpacity(0);
        turnLimit.setText("");
        turnLimit.setVisible(false);
        turnLimit.setOpacity(0);*/
    }

    @Override
    public void update() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}