package graphic.panel.seller.offs.show;

import graphic.Page;
import graphic.PageController;
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
