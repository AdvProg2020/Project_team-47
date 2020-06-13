package graphicView.panel.seller.products;

import graphicView.Page;
import graphicView.PageController;
import graphicView.panel.seller.offs.ManageOffsPage;
import graphicView.panel.seller.offs.ManageOffsPageController;

public class ManageProductsPage extends Page {
    private static Page page;

    private ManageProductsPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new ManageProductsPage("ManageProductsPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return ManageProductsPageController.getInstance();
    }
}
