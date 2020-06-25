package graphic.panel.customer.CustomerPurchaseHistory;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class CustomerPurchaseHistoryPage extends Page {
    private static Page page;
    protected CustomerPurchaseHistoryPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if(page==null)
            page = new CustomerPurchaseHistoryPage("/fxml/panel/customer/files/customerPurchaseHistory.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return CustomerPurchaseHistoryController.getInstance();
    }

}
