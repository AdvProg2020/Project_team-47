package graphic.panel.customer.cart;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class CustomerCartPage extends Page {
    private static Page page;

    protected CustomerCartPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if (page == null)
            page = new CustomerCartPage("/fxml/panel/customer/cart/customerCart.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return CustomerCartController.getInstance();
    }


}
