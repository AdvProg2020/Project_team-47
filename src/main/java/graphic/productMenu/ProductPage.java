package graphic.productMenu;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuController;
import graphic.mainMenu.MainMenuPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ProductPage extends Page {
    private static Page page;

    protected ProductPage(String scenePath) {
        super(scenePath);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if(page==null)
            page = new ProductPage("/fxml/product/product.fxml");
        return page;
    }

    @Override
    public PageController getController() {
        return ProductPageController.getInstance();
    }

}
