import com.google.gson.Gson;
import controller.Controller;
import controller.ControllerAndViewConnector;
import view.menu.UserMenu.customer.CustomerPanelMenu;

public class Main {
    public static void main(String[] args) {
        //new CustomerPanelMenu(null).autoExecute();
        Integer a = 23;
        ControllerAndViewConnector.setClientMessage((new Gson()).toJson(a));
        a = (new Gson()).fromJson(ControllerAndViewConnector.getAnswer(), Integer.class);
        System.out.println(a);
    }
}
