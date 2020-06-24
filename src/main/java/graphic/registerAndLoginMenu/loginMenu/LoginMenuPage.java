package graphic.registerAndLoginMenu.loginMenu;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class LoginMenuPage extends Page {
    private static Page page;


    protected LoginMenuPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    @Override
    public PageController getController() {
        return LoginMenuController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new LoginMenuPage("/fxml/registerAndLoginMenu/loginMenu.fxml");
        return page;
    }
}
