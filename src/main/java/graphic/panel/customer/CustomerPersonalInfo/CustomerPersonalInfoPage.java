package graphic.panel.customer.CustomerPersonalInfo;

import graphic.Page;
import graphic.PageController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryController;
import graphic.panel.customer.CustomerPurchaseHistory.CustomerPurchaseHistoryPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class CustomerPersonalInfoPage extends Page {
    private static Page page;
    protected CustomerPersonalInfoPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if(page==null)
            page = new CustomerPersonalInfoPage("/fxml/panel/customer/files/customerPersonalInfo.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return CustomerPersonalInfoController.getInstance();
    }
}
