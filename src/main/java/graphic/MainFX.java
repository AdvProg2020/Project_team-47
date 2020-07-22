package graphic;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainFX {
    private static MainFX mainFX;
    private final ArrayList<Scene> scenes;
    private MediaPlayer backGroundMediaPlayer;
    private MediaPlayer shortMediaPlayer;
    private Stage window;
    private boolean loggedIn;
    private String accountType;//can be seller,customer,manager
    private String myUsername;
    private Image avatar;
    private String token;

    private MainFX() {
        scenes = new ArrayList<>();
    }

    public static MainFX getInstance() {
        if (mainFX == null)
            mainFX = new MainFX();
        return mainFX;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image image) {
        this.avatar = image;
    }

    public void start(Stage window) throws Exception {
        this.window = window;
        window.setOnCloseRequest((event) -> exit());
        window.setTitle("AP SHOP");
        window.setResizable(false);
        playBackGroundAudio("background1.mp3");
        goToFirstPage();
        window.show();
    }

    private void exit() {
        try {
            Client.closeSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void changeScene(Scene scene) {
        scenes.add(scene);
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
        scenes.add(MainPage.getScene());
        window.setScene(scenes.get(0));
    }

    public Stage getWindow() {
        return window;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public void playBackGroundAudio(String audioPath) {
        Media media = new Media(Paths.get("src\\main\\resources\\Music\\" + audioPath).toUri().toString());
        backGroundMediaPlayer = new MediaPlayer(media);
//        backGroundMediaPlayer.play();
        backGroundMediaPlayer.setOnEndOfMedia(() -> playBackGroundAudio(audioPath));
    }

    public void click() {
        Media media = new Media(Paths.get("src\\main\\resources\\Music\\click.wav").toUri().toString());
        shortMediaPlayer = new MediaPlayer(media);
        shortMediaPlayer.play();
    }

    public boolean getLoginStatus() {
        return loggedIn;
    }
}
