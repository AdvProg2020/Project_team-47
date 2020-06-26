package graphic.panel.customer.CustomerPersonalInfo;

import graphic.PageController;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPersonalInfoController extends PageController {

    private static PageController controller;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new CustomerPersonalInfoController();
        }
        return controller;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
