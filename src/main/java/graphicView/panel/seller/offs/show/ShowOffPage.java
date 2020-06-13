package graphicView.panel.seller.offs.show;

import graphicView.Page;
import graphicView.PageController;
import graphicView.panel.seller.offs.ManageOffsPage;
import graphicView.panel.seller.offs.ManageOffsPageController;
import model.send.receive.OffInfo;

public class ShowOffPage extends Page {
    private static Page page;

    private ShowOffPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new ShowOffPage("/fxml/panel/seller/off/show/ShowOffPage.fxml");
        return page;
    }

    public void initializePage(OffInfo offInfo) {
        ShowOffPageController.getInstance().initializeOff(offInfo);
    }

    @Override
    public PageController getController() {
        return ShowOffPageController.getInstance();
    }
}
