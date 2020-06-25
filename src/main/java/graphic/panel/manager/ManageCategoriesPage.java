package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.send.receive.CategoryInfo;
import model.send.receive.ClientMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCategoriesPage extends PageController {
    @FXML
    private VBox vBox;

    public static Scene getScene() {
        return getScene("/fxml/panel/manager/ManageCategoriesPage.fxml");
    }

    @FXML
    private void back() {
        GraphicView.getInstance().back();
    }

    @FXML
    private void addCategory() {
        GraphicView.getInstance().changeScene(AddCategoryPage.getScene());
    }

    @FXML
    private void editCategory() {
        GraphicView.getInstance().changeScene(EditCategoryPage.getScene());
    }

    @Override
    public void clearPage() {

    }

    @Override
    public void update() {
        vBox.getChildren().clear();
        ClientMessage request = new ClientMessage("manage categories");
        updateVBox(send(request).getCategoryInfoArrayList());
    }

    private void updateVBox(ArrayList<CategoryInfo> categories) {
        for (CategoryInfo category : categories) {
            Label categoryLabel = new Label(category.getName());
            categoryLabel.setFont(new Font(18));
            vBox.getChildren().add(categoryLabel);
            for (String subCategory : category.getSubCategories()) {
                Label subCategoryLabel = new Label("     " + subCategory);
                subCategoryLabel.setFont(new Font(15));
                vBox.getChildren().add(subCategoryLabel);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
