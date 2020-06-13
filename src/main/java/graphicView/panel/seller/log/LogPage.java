package graphicView.panel.seller.log;

import graphicView.Page;
import graphicView.PageController;

public class LogPage extends Page {
    private static Page page;

    private LogPage(String scenePath) {
        super(scenePath);
    }

    public static Page getInstance() {
        if(page==null)
            page = new LogPage("/fxml/panel/seller/log/LogsPage.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return LogsPageController.getInstance();
    }
}
