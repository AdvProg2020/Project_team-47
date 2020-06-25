package graphic.registerAndLoginMenu.registerAndLogin;

import graphic.Page;
import graphic.PageController;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterAndLoginPage extends Page {
    private static Page page;
    private static Page nextPageForCustomer;
    private static Page nextPageForSeller;
    protected RegisterAndLoginPage(String scenePath, Page nextPageForCustomer, Page nextPageForSeller) {
        super(scenePath);
        RegisterAndLoginPage.nextPageForCustomer = nextPageForCustomer;
        RegisterAndLoginPage.nextPageForSeller = nextPageForSeller;
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
    }

    public static Page getNextPageForCustomer() {
        return nextPageForCustomer;
    }

    public static Page getNextPageForSeller() {
        return nextPageForSeller;
    }

    @Override
    public PageController getController() {
        return RegisterAndLoginController.getInstance();
    }

    public static Page getInstance(Page nextPageForCustomer, Page nextPageForSeller) {
        if(page==null)
            page = new RegisterAndLoginPage("/fxml/registerAndLoginMenu/registerAndLogin.fxml", nextPageForCustomer, nextPageForSeller);
        return page;
    }
}
