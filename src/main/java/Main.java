import com.google.gson.Gson;
import controller.Controller;
import controller.ControllerAndViewConnector;
import view.menu.UserMenu.customer.CustomerPanelMenu;

public class Main {
    public static void main(String[] args) {
        new CustomerPanelMenu(null).autoExecute();
    }
}
