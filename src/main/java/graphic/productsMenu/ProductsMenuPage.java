package graphic.productsMenu;

import graphic.Page;
import graphic.PageController;
import graphic.mainMenu.MainMenuController;
import graphic.mainMenu.MainMenuPage;
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
            page = new ProductsMenuPage("/fxml/mainMenu/mainMenu.fxml");
        return page;
    }


    @Override
    public PageController getController() {
        return MainMenuController.getInstance();
    }
}
