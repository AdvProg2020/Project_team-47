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

import java.net.URL;
import java.util.ArrayList;
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
        //todo amir get comments
        /*ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : comments) {
            TextArea textArea = new TextArea();
            // add commenter + isBuyer + comment
            //textArea.setText();
            textArea.setEditable(false);
            vBox.getChildren().add(textArea);
        }*/
        Comment comment = new Comment();
        comment.setCommentText("salam salam salam");
        comment.setWhoComment("ali1");
        TextArea textArea = new TextArea();
        textArea.setText("buyer: " + comment.getWhoComment() + "\n" + comment.getCommentText());
        textArea.setEditable(false);
        vBox.getChildren().add(textArea);

        Comment comment1 = new Comment();
        comment1.setCommentText("salam salam salam1ertfgserfgouisrhgosr" +
                "sg;snhghsiughiushgiusgssgsgsdfgsfdgsdgsfgs\n\nsaegfsrghedthedfthjdttjhdt\ndethdthdthdthd" +
                "\ndtrhtdhdthdfthdthdthed\ndthdhdthdthdthdt\ndthdthdthdthdt\ndthdthdhdt\ntdhdthdthd");
        comment1.setWhoComment("ali11");
        TextArea textArea1 = new TextArea();
        textArea1.setText("customer: " + comment1.getWhoComment() + "\n" + comment1.getCommentText());
        textArea1.setEditable(false);
        vBox.getChildren().add(textArea1);

        Comment comment3 = new Comment();
        comment3.setCommentText("salam salam salam");
        comment3.setWhoComment("ali1");
        TextArea textArea3 = new TextArea();
        textArea3.setText("buyer: " + comment3.getWhoComment() + "\n" + comment3.getCommentText());
        textArea3.setEditable(false);
        vBox.getChildren().add(textArea3);

        Comment comment2 = new Comment();
        comment2.setCommentText("salam salam salam1");
        comment2.setWhoComment("ali11");
        TextArea textArea12 = new TextArea();
        textArea12.setText("customer: " + comment2.getWhoComment() + "\n" + comment2.getCommentText());
        textArea12.setEditable(false);
        vBox.getChildren().add(textArea12);

        Comment comment22 = new Comment();
        comment22.setCommentText("salam salam salam1");
        comment22.setWhoComment("ali11");
        TextArea textArea122 = new TextArea();
        textArea122.setText("customer: " + comment22.getWhoComment() + "\n" + comment22.getCommentText());
        textArea122.setEditable(false);
        vBox.getChildren().add(textArea122);

        Comment comment23 = new Comment();
        comment23.setCommentText("salam salam salam1");
        comment23.setWhoComment("ali11");
        TextArea textArea123 = new TextArea();
        textArea123.setText("customer: " + comment23.getWhoComment() + "\n" + comment23.getCommentText());
        textArea123.setEditable(false);
        vBox.getChildren().add(textArea123);
    }

    public void back(MouseEvent mouseEvent) {
        GraphicView.getInstance().back();
    }

}
