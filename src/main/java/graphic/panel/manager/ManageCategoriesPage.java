package graphic.panel.manager;

import graphic.GraphicView;
import graphic.PageController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.send.receive.CategoryInfo;
import model.send.receive.ClientMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
            categoryLabel.setOnMouseClicked(e->onCategoryClick(e,"main category",category.getName()));
            categoryLabel.setFont(new Font(18));
            vBox.getChildren().add(categoryLabel);
            for (String subCategory : category.getSubCategories()) {
                Label subCategoryLabel = new Label("     " + subCategory);
                subCategoryLabel.setOnMouseClicked(e -> onCategoryClick(e, "sub category", subCategory));
                subCategoryLabel.setFont(new Font(15));
                vBox.getChildren().add(subCategoryLabel);
            }
        }
    }

    private void onCategoryClick(MouseEvent event, String categoryType,String name) {
        if (event.getButton() == MouseButton.SECONDARY) {
            ClientMessage request = new ClientMessage("remove " + categoryType);
            HashMap<String, String> reqInfo = new HashMap<>();
            reqInfo.put("category name", name);
            request.setHashMap(reqInfo);
            send(request);
            update();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
}
