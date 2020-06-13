package graphicView.panel.seller.offs;

import graphicView.Page;
import graphicView.PageController;
import graphicView.panel.seller.log.LogPage;

public class ManageOffsPage extends Page {
    private static Page page;

    private ManageOffsPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new ManageOffsPage("ManageOffsPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return ManageOffsPageController.getInstance();
    }
}
