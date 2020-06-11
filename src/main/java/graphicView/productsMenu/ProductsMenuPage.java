package graphicView.productsMenu;

import graphicView.GraphicView;
import graphicView.Page;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductsMenuPage extends Page {
    public ProductsMenuPage(Page previousPage) {
        super(previousPage, new ProductsMenuController(), "productsMenu.fxml");
        /*try {
            this.scene = FXMLLoader.load(getClass().getResource("productsMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //super.start();
        System.out.println("salam");
        this.getController().update();

        GraphicView graphicView = GraphicView.getAllViews().get(0);

        Stage stage = graphicView.getStage();

        if (stage == null) {
            System.out.println("salsamsdgsf");
        }

        stage.setScene(this.getScene());
        stage.show();

    }
}
