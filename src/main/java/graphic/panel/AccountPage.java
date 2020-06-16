package graphic.panel;

import graphic.Page;
import graphic.PageController;
import graphic.panel.seller.SellerPageController;

public class AccountPage extends Page {
    private static Page page;

    private AccountPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new AccountPage("/fxml/panel/AccountPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return SellerPageController.getInstance();
    }

}
