package graphic.panel.seller.products;

import graphic.GraphicView;
import graphic.PageController;
import graphic.panel.ProductPane;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import model.send.receive.ClientMessage;
import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellerProductPage extends PageController {
    private static PageController controller;
    @FXML
    private Pagination pagination;

    public static PageController getInstance() {
        return controller;
    }

    public static Scene getScene() {
        return getScene("/fxml/panel/seller/products/show/SellerProductsPage.fxml");
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        update();
        ClientMessage request = new ClientMessage("manage products seller");
        ServerMessage answer = send(request);
        initializePagination(answer.getProductInfoArrayList());
    }

    private void initializePagination(ArrayList<ProductInfo> productInfoArrayList) {
        pagination.setPageCount(productInfoArrayList.size() / 3 + 1);
        pagination.setPageFactory((Integer pageNum) -> pageInitialize(pageNum, productInfoArrayList));
    }

    private VBox pageInitialize(int pageNum, ArrayList<ProductInfo> productInfoArrayList) {
        VBox vBox = new VBox();
        try {
            for (int i = 0; i < 3; i++) {
                vBox.getChildren().add(ProductPane.getPane(productInfoArrayList.get(pageNum * 3 + i)));
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return vBox;
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

}

