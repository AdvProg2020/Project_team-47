package graphic.productMenu.menus;

import graphic.GraphicView;
import graphic.PageController;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.others.Comment;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCommentsController extends PageController {

    private static PageController controller;

    public VBox vBox;

    public static PageController getInstance() {
        if (controller == null) {
            controller = new ProductCommentsController();
        }
        return controller;
    }

    public static Scene getScene() {
        Scene scene = getScene("/fxml/product/productComments.fxml");
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        return scene;
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServerMessage serverMessage = send(new ClientMessage());

        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : comments) {
            TextArea textArea = new TextArea();
            // add commenter + isBuyer + comment
            //textArea.setText();
            textArea.setEditable(false);
            vBox.getChildren().add(textArea);
        }

    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

    public void addComment(MouseEvent mouseEvent) {
        //todo amir send message to controller
        update();
    }
}
