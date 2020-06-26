package graphic.panel;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.send.receive.ProductInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductPane implements Initializable {
    private static AnchorPane anchorPane;
    private static ProductPane pane;
    private static ProductInfo productInfo;
    @FXML
    private Text id;
    @FXML
    private Text name;
    @FXML
    private Text price;
    @FXML
    private Text category;
    @FXML
    private Label subCategoryLabel;
    @FXML
    private Text subCategory;
    @FXML
    private Label numberInStockLabel;
    @FXML
    private Text numberInStock;
    @FXML
    private ImageView imageView;

    public ProductPane() {
    }


    public static void setProduct(ProductInfo productInfo) {
        ProductPane.productInfo = productInfo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.category.setText(productInfo.getMainCategory());
        this.imageView.setImage(PageController.byteToImage(productInfo.getFile()));
        this.id.setText(productInfo.getId());
        this.name.setText(productInfo.getName());
        this.numberInStock.setText("" + productInfo.getNumberInStock(GraphicView.getInstance().getMyUsername()));
        this.price.setText(productInfo.getPrice(GraphicView.getInstance().getMyUsername()) + "");
        if (productInfo.getSubCategory().isEmpty()) {
            this.subCategory.setVisible(false);
            this.subCategoryLabel.setVisible(false);
        } else {
            this.subCategory.setText(productInfo.getSubCategory());
        }
    }
}

