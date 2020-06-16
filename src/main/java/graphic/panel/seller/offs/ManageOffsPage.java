package graphic.panel.seller.offs;

import graphic.Page;
import graphic.PageController;

public class ManageOffsPage extends Page {
    private static Page page;

    private ManageOffsPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new ManageOffsPage("/fxml/panel/seller/off/ManageOffsPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return ManageOffsPageController.getInstance();
    }
}
