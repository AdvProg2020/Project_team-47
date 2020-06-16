package graphic.panel.seller;

import graphic.Page;
import graphic.PageController;

public class SellerPage extends Page {
    private static Page page;

    private SellerPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new SellerPage("/fxml/panel/seller/SellerPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return SellerPageController.getInstance();
    }
}
