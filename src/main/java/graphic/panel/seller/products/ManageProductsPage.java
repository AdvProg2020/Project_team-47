package graphic.panel.seller.products;

import graphic.Page;
import graphic.PageController;

public class  ManageProductsPage extends Page {
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
