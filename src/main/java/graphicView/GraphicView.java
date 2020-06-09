package graphicView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphicView extends Application{
    @Override
    public void start(Stage stage) throws Exception {

        setScene(stage);
        stage.show();
    }

    private void setScene(Stage stage) {
        BorderPane pane = new BorderPane();
        stage.setTitle("register menu");
        Scene scene = new Scene(pane, 440, 490);
        stage.setScene(scene);
    }

    public void run(String[] args) {
        launch(args);
    }
}
