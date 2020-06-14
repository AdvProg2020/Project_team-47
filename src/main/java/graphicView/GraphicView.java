package graphicView;

import graphicView.mainMenu.MainMenuPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GraphicView {
    private static GraphicView graphicView;
    private final ArrayList<Scene> scenes;
    private Stage window;

    private GraphicView() {
        scenes = new ArrayList<>();
    }

    public static GraphicView getInstance() {
        if (graphicView == null)
            graphicView = new GraphicView();
        return graphicView;
    }

    public void start(Stage window) throws Exception {
        this.window = window;
        window.setOnCloseRequest((event) -> System.exit(0));
        window.setTitle("AP SHOP");
        window.setResizable(false);
        goToFirstPage();
        window.show();
    }

    public void changePage(Scene scene) {
        this.window.setScene(scene);
    }

    public void changeScene(Page page) {
        //this function will get a page and change window scene to page's scene
        Scene scene = page.getScene();
        scenes.add(page.getScene());
        page.getController().update();
        window.setScene(scene);
    }

    public void back() {
        //this function will change scene to last scene which user was in it
        int scenesSize = scenes.size();
        scenes.remove(--scenesSize);
        window.setScene(scenes.get(scenesSize - 1));
    }

    public void goToFirstPage() {
        //this function will change window scene to first scene(login scene)
        scenes.clear();

        scenes.add(MainMenuPage.getInstance().getScene());
        window.setScene(scenes.get(0));
    }

    public Stage getWindow() {
        return window;
    }

}
