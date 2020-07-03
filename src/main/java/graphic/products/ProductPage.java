package graphic.products;

import graphic.MainFX;
import graphic.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.others.Comment;
import model.others.SpecialProperty;
import model.send.receive.ClientMessage;
import model.send.receive.ProductInfo;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductPage extends PageController {
    private static ProductInfo product;

    @FXML
    private VBox secondVBox;
    @FXML
    private VBox firstVBox;
    @FXML
    private AnchorPane secondPane;
    @FXML
    private AnchorPane firstPane;
    @FXML
    private TextField seller;
    @FXML
    private TextField scoreTextField;
    @FXML
    private TextField comment;
    @FXML
    private TextField productId;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label category;
    @FXML
    private Label subcategory;
    @FXML
    private VBox sellers;
    @FXML
    private Label score;
    @FXML
    private TextArea description;
    @FXML
    private VBox properties;
    @FXML
    private VBox comments;

    public static void setProduct(ProductInfo product) {
        ProductPage.product = product;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (product == null) return;
        new Thread(() -> image.setImage(PageController.byteToImage(product.getFile()))).start();
        if (product.isItInOff()) image.setEffect(new SepiaTone());
        name.setText("Name: " + product.getName());
        id.setText("Id: " + product.getId());
        category.setText("Category: " + product.getMainCategory());
        subcategory.setText("Subcategory: " + product.getSubCategory());
        score.setText("Score: " + product.getScoreAverage());
        description.setEditable(false);
        description.setText(product.getDescription());
        initializeProperties(product.getSpecialProperties());
        initializeSellers();
        initializeComments();
    }

    private void initializeComments() {
        ClientMessage request = new ClientMessage("show product");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("product id", product.getId());
        request.setHashMap(reqInfo);
        send(request);
        request.setType("comments");
        comments.getChildren().clear();
        for (Comment comment : send(request).getCommentArrayList()) {
            HBox hBox = new HBox(4);
            hBox.getChildren().addAll(new Label(comment.getWhoComment(), new Label(comment.getCommentText())));
            comments.getChildren().add(hBox);
        }
    }

    private void initializeSellers() {
        sellers.getChildren().clear();
        for (String sellerName : product.getSellersNames()) {
            HBox hBox = new HBox(5);
            hBox.getChildren().addAll(new Label(sellerName), new Label("" + product.getPrice(sellerName)));
            sellers.getChildren().add(hBox);
        }
    }

    private void initializeProperties(ArrayList<SpecialProperty> properties) {
        this.properties.getChildren().clear();
        for (SpecialProperty property : properties) {
            HBox hBox = new HBox(5);
            hBox.getChildren().add(new Label(property.getKey()));
            if (property.getType().equals("text")) {
                hBox.getChildren().add(new Label(property.getValue()));
            } else {
                hBox.getChildren().addAll(new Label(property.getNumericValue() + ""), new Label(property.getUnit()));
            }
            this.properties.getChildren().add(hBox);
        }
    }

    public void compare() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("compare products");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("id", productId.getText());
        request.setHashMap(reqInfo);
        ServerMessage answer = sendAndProcess(request);
        if (answer == null) return;
        compare(answer.getProductInfoArrayList().get(0), answer.getProductInfoArrayList().get(1));
    }

    private void compare(ProductInfo firstProduct, ProductInfo secondProduct) {
        firstPane.setVisible(false);
        secondPane.setVisible(true);
        firstVBox.getChildren().clear();
        secondVBox.getChildren().clear();
        firstVBox.getChildren().addAll(getCompareLabel("Price:", "" + firstProduct.getMinPrice()),
                getCompareLabel("Name:", firstProduct.getName()), getCompareLabel("score:", firstProduct.getScoreAverage() + ""),
                getCompareLabel("Id:", firstProduct.getId()));
        secondVBox.getChildren().addAll(getCompareLabel("Price:", "" + secondProduct.getMinPrice()),
                getCompareLabel("Name:", secondProduct.getName()), getCompareLabel("score:", secondProduct.getScoreAverage() + ""),
                getCompareLabel("Id:", secondProduct.getId()));

        for (SpecialProperty specialProperty : firstProduct.getSpecialProperties()) {
            Label label;
            if (specialProperty.getType().equals("text"))
                label = getCompareLabel(specialProperty.getKey() + ":", specialProperty.getValue());
            else
                label = getCompareLabel(specialProperty.getKey(), specialProperty.getNumericValue() + "", specialProperty.getUnit());
            firstVBox.getChildren().add(label);
        }
        for (SpecialProperty specialProperty : secondProduct.getSpecialProperties()) {
            Label label;
            if (specialProperty.getType().equals("text"))
                label = getCompareLabel(specialProperty.getKey() + ":", specialProperty.getValue());
            else
                label = getCompareLabel(specialProperty.getKey(), specialProperty.getNumericValue() + "", specialProperty.getUnit());
            secondVBox.getChildren().add(label);
        }
    }

    private Label getCompareLabel(String... text) {
        Label label = new Label();
        for (String s : text) {
            label.setText(label.getText() + " " + s);
        }
        label.setFont(new Font(18));
        return label;
    }

    public void comment() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("add comment");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("title", "");
        reqInfo.put("content", comment.getText());
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }

    private ServerMessage sendAndProcess(ClientMessage request) {
        ServerMessage answer = send(request);
        if (answer.getType().equals("Error")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error");
            alert.setContentText(answer.getErrorMessage());
            alert.showAndWait();
            return null;
        }
        return answer;
    }

    public void addToCart() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("add to cart");
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("seller username", seller.getText());
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }

    public void score() {
        MainFX.getInstance().click();
        ClientMessage request = new ClientMessage("score product");
        try {
            int score = Integer.parseInt(scoreTextField.getText());
            request.setFirstInt(score);
        } catch (NumberFormatException e) {
            scoreTextField.setText("Please enter an integer between 1 to 5");
            return;
        }
        HashMap<String, String> reqInfo = new HashMap<>();
        reqInfo.put("product id", product.getId());
        request.setHashMap(reqInfo);
        sendAndProcess(request);
    }

    @FXML
    private void productPage(ActionEvent actionEvent) {
        firstPane.setVisible(true);
        secondPane.setVisible(false);
    }
}
