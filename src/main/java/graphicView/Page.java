package graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Page extends Application {
    protected Scene scene;
    protected PageController controller;
    protected Page previousPage;

    public Page(Page previousPage, PageController controller, String scene) {
        this.previousPage = previousPage;
        this.controller = controller;
        this.controller.setPage(this);
        try {
            this.scene = FXMLLoader.load(getClass().getResource(scene));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return this.scene;
    }

    public PageController getController() {
        return this.controller;
    }

    public void start() {
        this.getController().update();

        GraphicView graphicView = GraphicView.getAllViews().get(0);

        Stage stage = GraphicView.getStage();


        if (stage == null) {
            System.out.println("salsamsdgsf");
        }

        stage.setScene(this.getScene());
        stage.show();

    }

}
