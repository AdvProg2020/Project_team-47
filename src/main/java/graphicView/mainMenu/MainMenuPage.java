package graphicView.mainMenu;

import graphicView.Page;
import graphicView.PageController;
import graphicView.panel.seller.SellerPage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainMenuPage extends Page {
    private static Page page;

    public MainMenuPage(String path) {
        super(path);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getInstance() {
        if(page==null)
            page = new MainMenuPage("/fxml/mainMenu/mainMenu.fxml");
        return page;
    }


    @Override
    public PageController getController() {
        return MainMenuController.getInstance();
    }
}
