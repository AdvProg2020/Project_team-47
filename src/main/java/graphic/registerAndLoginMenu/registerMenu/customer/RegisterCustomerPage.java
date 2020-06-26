package graphic.registerAndLoginMenu.registerMenu.customer;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import graphic.registerAndLoginMenu.registerMenu.RegisterMenuController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterCustomerPage extends Page {
    private static Page page;
    protected RegisterCustomerPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    @Override
    public PageController getController() {
        return RegisterMenuController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new RegisterCustomerPage("/fxml/registerAndLoginMenu/registerCustomer.fxml");
        return page;
    }

}
