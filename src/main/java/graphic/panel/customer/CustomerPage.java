package graphic.panel.customer;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class CustomerPage extends Page {
    private static Page page;
    protected CustomerPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if(page==null)
            page = new CustomerPage("/fxml/panel/customer/customerPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return CustomerPageController.getInstance();
    }
}
