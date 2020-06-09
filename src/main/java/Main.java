import database.Database;
import graphicView.GraphicView;
import view.menu.UserMenu.customer.CustomerPanelMenu;

public class Main {
    public static void main(String[] args) {
        //loading database
        //new Database().startDatabaseLoading();

        //with console
        //new CustomerPanelMenu(null).autoExecute();

        //with javaFX
        new GraphicView().run(args);

    }
}
