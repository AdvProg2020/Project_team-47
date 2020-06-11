package graphicView;

import graphicView.mainMenu.MainMenuPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.nio.file.Paths;
import java.util.ArrayList;

public class GraphicView extends Application {
    public static ArrayList <GraphicView> allViews = new ArrayList<>();
    private static Stage stage;
    private boolean managerRegistered = false;
    public static double SCREEN_HEIGHT;
    public static double SCREEN_WIDTH;



    public GraphicView() {
        allViews.add(this);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        if (!managerRegistered) {
            //todo
        }

        setStageProperties();


        MainMenuPage mainMenuPage = new MainMenuPage(null);
        Scene mainMenuScene = mainMenuPage.getScene();
        mainMenuPage.getController().update();
        stage.setScene(mainMenuScene);
        stage.show();
    }

    public void setStageProperties() {
        SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
        SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
        stage.setTitle("main menu");
        stage.setOnCloseRequest(e -> System.exit(0));

        stage.setHeight(SCREEN_HEIGHT * 2/3);
        stage.setWidth(SCREEN_WIDTH * 2/3);

        stage.setResizable(false);
    }

    public void run(String[] args) {
        launch(args);
    }

    MediaPlayer backGroundMediaPlayer;
    public void playBackGroundAudio(String audioPath) {
        Media media = new Media(Paths.get("src\\sound\\" + audioPath).toUri().toString());
        backGroundMediaPlayer = new MediaPlayer(media);
        backGroundMediaPlayer.play();

        backGroundMediaPlayer.setOnEndOfMedia(() -> playBackGroundAudio("src\\sound\\" + audioPath));

    }

    MediaPlayer shortMediaPlayer;
    public void playShortAudios(String audioPath) {
        Media media = new Media(Paths.get("src\\sound\\" + audioPath).toUri().toString());
        shortMediaPlayer = new MediaPlayer(media);
        shortMediaPlayer.play();
    }

    public static Stage getStage() {
        return stage;
    }

    public static ArrayList<GraphicView> getAllViews() {
        return allViews;
    }

    public static void setAllViews(ArrayList<GraphicView> allViews) {
        GraphicView.allViews = allViews;
    }



}
