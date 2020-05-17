import database.Database;
import view.menu.UserMenu.customer.CustomerPanelMenu;

public class Main {
    public static void main(String[] args) {
        new Database().startDatabaseLoading();
        new CustomerPanelMenu(null).autoExecute();
    }
}
