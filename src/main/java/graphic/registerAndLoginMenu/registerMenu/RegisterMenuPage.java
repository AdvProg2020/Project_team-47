package graphic.registerAndLoginMenu.registerMenu;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterMenuPage extends Page {
    private static Page page;

    protected RegisterMenuPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if (page == null)
            page = new RegisterMenuPage("/fxml/registerAndLoginMenu/registerMenu.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return RegisterMenuController.getInstance();
    }
}
