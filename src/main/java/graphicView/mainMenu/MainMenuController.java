package graphicView.mainMenu;

import graphicView.PageController;
import graphicView.productsMenu.ProductsMenuPage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController extends PageController {

    /*@FXML
    private TextField username;
    @FXML
    private TextField undoLimit;
    @FXML
    private TextField turnLimit;
    @FXML
    private TextField time;*/
    @FXML
    private Label errorLabel;


    @FXML
    private void exit() {
        /*
        Message req = new Message("logout");
        if (send(req)) {
            clearPage();
            MainView.getInstance().changeScene(LoginPage.getInstance());
        }*/
        System.exit(0);
    }

    @FXML
    private void loginAndRegister() {

        /*Message req = new Message("new game");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("username", username.getText());
        reqInfo.put("turn limit", turnLimit.getText());
        reqInfo.put("undo limit", undoLimit.getText());
        reqInfo.put("time", time.getText());
        req.setReqInfo(reqInfo);
        if (send(req)) {
            clearPage();
            MainView.getInstance().changeScene(GamePage.getNewInstance());
        }*/
    }

    @FXML
    private void products() {
        ProductsMenuPage page = new ProductsMenuPage(this.getPage());
        page.start();
        /*
        clearPage();
        MainView.getInstance().changeScene(ScoreBoardPage.getInstance());
        */
    }

    @FXML
    private void offs() {
        /*
        clearPage();
        MainView.getInstance().changeScene(AccountPage.getInstance());*/
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
    public void processError(Exception e) {
        errorLabel.setText(e.getMessage());
        errorLabel.setVisible(true);
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