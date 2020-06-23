import database.Database;
import graphic.GraphicView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        //new Database().startDatabaseLoading();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphicView.getInstance().start(primaryStage);
    }
}
