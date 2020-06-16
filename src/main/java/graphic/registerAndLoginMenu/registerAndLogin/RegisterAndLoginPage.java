package graphic.registerAndLoginMenu.registerAndLogin;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;

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
