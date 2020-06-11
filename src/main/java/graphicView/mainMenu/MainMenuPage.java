package graphicView.mainMenu;

import graphicView.Page;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuPage extends Page {

    public MainMenuPage(Page previousPage) {
        super(previousPage, new MainMenuController(), "mainMenu.fxml");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start();
    }
}
