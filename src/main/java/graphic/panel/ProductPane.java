package graphic.panel;

import graphic.MainFX;
import graphic.PageController;
import graphic.TemplatePage;
import graphic.products.ProductPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.send.receive.ProductInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductPane implements Initializable {
    private static ProductInfo productInfo;
    private static ProductInfo productPage;
    private static ProductInfo cartProduct;
    private static String seller;
    private static int numberInCart;

    private ProductInfo paneProduct;


    @FXML
    private Text finalPrice;
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

    public static void setCart(ProductInfo cartProduct, String seller, int numberInCart) {
        productInfo = null;
        productPage = null;
        ProductPane.cartProduct = cartProduct;
        ProductPane.seller = seller;
        ProductPane.numberInCart = numberInCart;
    }

    public static void setProduct(ProductInfo productInfo) {
        productPage = null;
        cartProduct = null;
        ProductPane.productInfo = productInfo;
    }

    public static void setProductPage(ProductInfo productPage) {
        productInfo = null;
        cartProduct = null;
        ProductPane.productPage = productPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (productPage != null) {
            initializeForProductPage(productPage);
            paneProduct = productPage;
        } else if (productInfo != null) {
            paneProduct = productInfo;
            initializeForSellerProduct(productInfo);
        } else if (cartProduct != null) {
            paneProduct = cartProduct;
            initializeForCartProduct(cartProduct);
        }
        pane.setOnMouseClicked(e -> goToProductPage());
    }

    private void initializeForCartProduct(ProductInfo productInfo) {
        this.category.setText(productInfo.getMainCategory());
        new Thread(() -> this.imageView.setImage(PageController.byteToImage(productInfo.getFile()))).start();
        this.score.setText(productInfo.getScoreAverage() + "");
        this.name.setText(productInfo.getName());
        this.id.setText(productInfo.getId());
        this.price.setText(productInfo.getFinalPrice(seller) + "");
        this.numberInStock.setText("" + numberInCart);
        this.numberInStockLabel.setText("Number In Cart: ");
        this.finalPrice.setText(finalPrice.getText() + productInfo.getFinalPrice(seller) * numberInCart);
        this.finalPrice.setVisible(true);
        if (productInfo.getSubCategory().isEmpty()) {
            this.subCategory.setVisible(false);
            this.subCategoryLabel.setVisible(false);
        } else {
            this.subCategory.setText(productInfo.getSubCategory());
        }
    }

    private void goToProductPage() {
        try {
            ProductPage.setProduct(paneProduct);
            MainFX.getInstance().changeScene(TemplatePage.getScene());
            TemplatePage.getInstance().changePane(FXMLLoader.load(getClass().getResource("/fxml/products/Product.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeForProductPage(ProductInfo productInfo) {
        this.category.setText(productInfo.getMainCategory());
        this.score.setText(productInfo.getScoreAverage() + "");
        new Thread(() -> this.imageView.setImage(PageController.byteToImage(productInfo.getFile()))).start();
        this.id.setText(productInfo.getId());
        this.name.setText(productInfo.getName());
        this.numberInStock.setText("" + productInfo.getNumberInStock());
        this.price.setText(getPrice(productInfo));
        if (productInfo.getNumberInStock() <= 0) {
            this.imageView.setEffect(new MotionBlur(0, 10));
        }
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
        new Thread(() -> this.imageView.setImage(PageController.byteToImage(productInfo.getFile()))).start();
        this.id.setText(productInfo.getId());
        this.name.setText(productInfo.getName());
        this.numberInStock.setText("" + productInfo.getNumberInStock(MainFX.getInstance().getMyUsername()));
        this.price.setText(productInfo.getPrice(MainFX.getInstance().getMyUsername()) + "");
        if (productInfo.getSubCategory().isEmpty()) {
            this.subCategory.setVisible(false);
            this.subCategoryLabel.setVisible(false);
        } else {
            this.subCategory.setText(productInfo.getSubCategory());
        }
    }
}

