package graphic.productsMenu;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ProductsMenuPage extends Page {
    private static Page page;

    public ProductsMenuPage(String path) {
        super(path);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if(page==null)
            page = new ProductsMenuPage("/fxml/products/productsMenu.fxml");
        return page;
    }


    @Override
    public PageController getController() {
        return ProductsMenuController.getInstance();
    }
}
