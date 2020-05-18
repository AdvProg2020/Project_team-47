import database.Database;
import view.menu.UserMenu.customer.CustomerPanelMenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        new Database().startDatabaseLoading();
        new CustomerPanelMenu(null).autoExecute();
    }
}
