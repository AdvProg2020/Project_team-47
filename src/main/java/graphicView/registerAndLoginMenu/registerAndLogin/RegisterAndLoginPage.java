package graphicView.registerAndLoginMenu.registerAndLogin;

import graphicView.Page;
import graphicView.PageController;
import graphicView.mainMenu.MainMenuPage;

public class RegisterAndLoginPage extends Page {
    private static Page page;
    protected RegisterAndLoginPage(String scenePath) {
        super(scenePath);
    }

    @Override
    public PageController getController() {
        return RegisterAndLoginController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new MainMenuPage("/fxml/registerAndLoginMenu/registerAndLogin.fxml");
        return page;
    }
}
