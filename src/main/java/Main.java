import database.Database;
import graphicView.GraphicView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        //loading database
        new Database().startDatabaseLoading();

        //with console
        //new CustomerPanelMenu(null).autoExecute();

        //with javaFX
        GraphicView.getInstance().run(args);
    }
}
