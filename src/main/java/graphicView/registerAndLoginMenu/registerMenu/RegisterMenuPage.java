package graphicView.registerAndLoginMenu.registerMenu;

import graphicView.Page;
import graphicView.PageController;
import graphicView.mainMenu.MainMenuPage;

public class RegisterMenuPage extends Page {
    private static Page page;

    protected RegisterMenuPage(String scenePath) {
        super(scenePath);
    }

    @Override
    public PageController getController() {
        return RegisterMenuController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new MainMenuPage("/fxml/registerAndLoginMenu/registerMenu.fxml");
        return page;
    }
}
