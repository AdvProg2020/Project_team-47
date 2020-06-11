package graphicView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private static Main main;
    private final ArrayList<Scene> scenes;
    private Stage window;

    public Main() {
        scenes = new ArrayList<>();
    }

    public static Main getInstance() {
        if (main == null)
            main = new Main();
        return main;
    }

    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        window.setTitle("AP SHOP");
        window.setResizable(false);
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
        //scenes.add(LoginPage.getInstance().getScene());
        window.setScene(scenes.get(0));
    }

}