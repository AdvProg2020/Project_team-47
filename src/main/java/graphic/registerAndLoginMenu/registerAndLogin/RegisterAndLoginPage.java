package graphic.registerAndLoginMenu.registerAndLogin;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterAndLoginPage extends Page {
    private static Page page;
    protected RegisterAndLoginPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    @Override
    public PageController getController() {
        return RegisterAndLoginController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new RegisterAndLoginPage("/fxml/registerAndLoginMenu/registerAndLogin.fxml");
        return page;
    }
}
