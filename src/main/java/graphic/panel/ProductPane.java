package graphic.panel;

import graphic.GraphicView;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.productsMenu.ProductPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.send.receive.ProductInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductPane implements Initializable {
    private static ProductInfo productInfo;
    private static ProductInfo productPage;
    @FXML
    private AnchorPane pane;
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
    private Text score;
    @FXML
    private ImageView imageView;

    public ProductPane() {
    }


    public static void setProduct(ProductInfo productInfo) {
        ProductPane.productInfo = productInfo;
    }

    public static void setProductPage(ProductInfo productInfo) {
        productPage = productInfo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setOnMouseClicked(this::goToProductPage);
        if (productPage != null) {
            initializeForProductPage(productPage);
        } else if (productInfo != null) {
            initializeForSellerProduct(productInfo);
        }
    }

    private void goToProductPage(MouseEvent event) {
        try {
            if(productInfo!=null)
                ProductPage.setProduct(productInfo);
            else
                ProductPage.setProduct(productPage);
            GraphicView.getInstance().changeScene(TemplatePage.getScene());
            TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Product.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeForProductPage(ProductInfo productInfo) {
        this.category.setText(productInfo.getMainCategory());
        this.score.setText(productInfo.getScoreAverage() + "");
        this.imageView.setImage(PageController.byteToImage(productInfo.getFile()));
        this.id.setText(productInfo.getId());
        this.name.setText(productInfo.getName());
        this.numberInStock.setText("" + productInfo.getNumberInStock());
        this.price.setText(getPrice(productInfo));
        if (productInfo.getSubCategory().isEmpty()) {
            this.subCategory.setVisible(false);
            this.subCategoryLabel.setVisible(false);
        } else {
            this.subCategory.setText(productInfo.getSubCategory());
        }
    }

    private String getPrice(ProductInfo productInfo) {
        return productInfo.getMinPriceWithoutOff() + "," + productInfo.getMinPrice();
    }

    private void initializeForSellerProduct(ProductInfo productInfo) {
        this.category.setText(productInfo.getMainCategory());
        this.score.setText(productInfo.getScoreAverage() + "");
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

