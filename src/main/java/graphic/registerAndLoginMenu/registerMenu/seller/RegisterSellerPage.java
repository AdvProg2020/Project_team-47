package graphic.registerAndLoginMenu.registerMenu.seller;

import graphic.Page;
import graphic.PageController;
import graphic.registerAndLoginMenu.registerMenu.RegisterMenuController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterSellerPage extends Page {
    private static Page page;

    protected RegisterSellerPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if (page == null)
            page = new RegisterSellerPage("/fxml/registerAndLoginMenu/registerSeller.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return RegisterMenuController.getInstance();
    }
}
