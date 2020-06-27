package graphic.panel.customer.cart.purchasing;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class PurchasePage extends Page {
    private static Page page;

    protected PurchasePage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if (page == null)
            page = new PurchasePage("/fxml/panel/customer/cart/purchaseCart.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return PurchasePageController.getInstance();
    }
}
