package graphicView.panel.seller;

import graphicView.Page;
import graphicView.PageController;

public class SellerPage extends Page {
    private static Page page;

    private SellerPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new SellerPage("SellerPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return SellerPageController.getInstance();
    }
}
