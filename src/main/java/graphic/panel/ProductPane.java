package graphic.panel;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.send.receive.ProductInfo;

import java.io.IOException;

public class ProductPane {
    private static AnchorPane anchorPane;
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
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/fxml/panel/ProductPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AnchorPane getPane(ProductInfo productInfo) {
        ProductPane pane = new ProductPane();
        pane.category.setText(productInfo.getMainCategory());
        pane.imageView.setImage(PageController.byteToImage(productInfo.getFile()));
        pane.id.setText(productInfo.getId());
        pane.name.setText(productInfo.getName());
        pane.numberInStock.setText(""+productInfo.getNumberInStock(GraphicView.getInstance().getMyUsername()));
        pane.price.setText(productInfo.getPrice(GraphicView.getInstance().getMyUsername())+"");
        if (productInfo.getSubCategory().isEmpty()) {
            pane.subCategory.setVisible(false);
            pane.subCategoryLabel.setVisible(false);
        } else {
            pane.subCategory.setText(productInfo.getSubCategory());
        }
        return anchorPane;
    }
}

