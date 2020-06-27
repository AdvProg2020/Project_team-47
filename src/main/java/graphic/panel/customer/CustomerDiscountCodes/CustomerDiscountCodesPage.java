package graphic.panel.customer.CustomerDiscountCodes;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class CustomerDiscountCodesPage extends Page {
    private static Page page;

    protected CustomerDiscountCodesPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if (page == null)
            page = new CustomerDiscountCodesPage("/fxml/panel/customer/files/customerDiscountCodes.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return CustomerDiscountCodesController.getInstance();
    }
}
