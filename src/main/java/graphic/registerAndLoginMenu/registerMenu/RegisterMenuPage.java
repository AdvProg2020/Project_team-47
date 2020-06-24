package graphic.registerAndLoginMenu.registerMenu;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterMenuPage extends Page {
    private static Page page;

    protected RegisterMenuPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    @Override
    public PageController getController() {
        return RegisterMenuController.getInstance();
    }

    public static Page getInstance() {
        if(page==null)
            page = new RegisterMenuPage("/fxml/registerAndLoginMenu/registerMenu.fxml");
        return page;
    }
}
