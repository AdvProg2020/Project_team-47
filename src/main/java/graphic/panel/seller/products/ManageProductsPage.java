package graphic.panel.seller.products;

import com.sun.tools.javac.Main;
import graphic.MainFX;
import graphic.PageController;
import graphic.panel.seller.auction.AddAuctionPage;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManageProductsPage extends PageController {
    private static PageController controller;

    @FXML
    private TextField productId;
    @FXML
    private Text error;

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/products/ManageProductsPage.fxml");
    }

    public static PageController getInstance() {
        return controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
    }

    @Override
    public void clearPage() {
        productId.setText("");
        error.setText("");
    }

    @Override
    public void update() {
        clearPage();
    }

    public void showAllProducts() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(SellerProductPage.getScene());
    }

    public void hideTextField() {
        if (!error.isVisible())
            return;
        Thread thread = new Thread(() -> {
            createFadeAnimations(1, 0);
            error.setVisible(false);
            productId.setVisible(false);
        });
        thread.start();
    }

    public void showTextField() {
        if (error.isVisible())
            return;
        Thread thread = new Thread(() -> {
            error.setVisible(true);
            productId.setVisible(true);
            createFadeAnimations(0, 1);
        });
        thread.start();
    }

    public void editProduct() {
        MainFX.getInstance().click();
        if (productId.getText().isEmpty()) {
            error.setText("Product id can't be empty!!");
            return;
        }
        ClientMessage request = new ClientMessage("view product seller");
        HashMap<String, String> requestHashMap = new HashMap<>();
        requestHashMap.put("product id", productId.getText());
        request.setHashMap(requestHashMap);
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            error.setText(answer.getErrorMessage());
        } else {
            EditProductPage.setProductInfo(answer.getProductInfo());
            MainFX.getInstance().changeScene(EditProductPage.getScene());
        }
    }

    public void addAuction() {
        MainFX.getInstance().click();
        MainFX.getInstance().changeScene(AddAuctionPage.getScene());
    }

    public void addProduct() {
        MainFX.getInstance().click();
        clearPage();
        MainFX.getInstance().changeScene(AddProductPage.getScene());
    }

    public void back() {
        MainFX.getInstance().click();
        MainFX.getInstance().back();
    }

    private FadeTransition[] getFadingAnimation() {
        FadeTransition[] fadeTransitions = new FadeTransition[2];
        fadeTransitions[0] = new FadeTransition(Duration.millis(600), productId);
        fadeTransitions[1] = new FadeTransition(Duration.millis(600), error);
        return fadeTransitions;
    }

    private void createFadeAnimations(double from, double to) {
        FadeTransition[] fadeTransitions = getFadingAnimation();
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.setFromValue(from);
            fadeTransition.setToValue(to);
        }
        playFadeAnimation(fadeTransitions);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playFadeAnimation(FadeTransition[] fadeTransitions) {
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.play();
        }
    }
}
