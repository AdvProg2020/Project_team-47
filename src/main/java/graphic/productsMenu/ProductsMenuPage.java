package graphic.productsMenu;

import graphic.Page;
import graphic.PageController;

public class ProductsMenuPage extends Page {
    public ProductsMenuPage(Page previousPage) {
        super("productsMenu.fxml");
        /*try {
            this.scene = FXMLLoader.load(getClass().getResource("productsMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public PageController getController() {
        return null;
    }
}
