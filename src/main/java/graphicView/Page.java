package graphicView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public abstract class Page {
    protected Scene scene;

    protected Page(String scenePath) {
        try {
            this.scene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return this.scene;
    }

    public abstract PageController getController();

}
