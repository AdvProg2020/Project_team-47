package graphicView.offsMenu;

import graphicView.Page;
import graphicView.mainMenu.MainMenuController;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class OffsMenuPage extends Page {
    public OffsMenuPage(Page previousPage) {
        super(previousPage, new MainMenuController(), "offsMenu.fxml");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start();
    }
}
