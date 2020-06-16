package graphic.registerAndLoginMenu.loginMenu;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;

public class LoginMenuPage extends Page {
    private static Page page;


    protected LoginMenuPage(String scenePath) {
        super(scenePath);
    }

    @Override
    public PageController getController() {
        return LoginMenuController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new MainMenuPage("/fxml/registerAndLoginMenu/loginMenu.fxml");
        return page;
    }
}
