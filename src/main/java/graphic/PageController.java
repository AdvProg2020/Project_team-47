package graphic;

import controller.ControllerAndViewConnector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.io.IOException;


public abstract class PageController implements Initializable {

    protected static Scene getScene(String path) {
        return FxmlLoaderClass.getScene(path);
    }

    public ServerMessage send(ClientMessage request) {
        return ControllerAndViewConnector.commandProcess(request);
    }

    //this function will use to clear scenes
    public abstract void clearPage();

    //this function will update scene objects
    public abstract void update();

    public static Image byteToImage(byte[] imageBytes) {
        return null;
    }

    public static byte[] imageToByte(Image image) {
        return null;
    }

    private static class FxmlLoaderClass {
        private static final FxmlLoaderClass loader;

        static {
            loader = new FxmlLoaderClass();
        }

        private static Scene getScene(String path) {
            try {
                return loader.getSceneFrom(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Scene getSceneFrom(String path) throws IOException {
            return FXMLLoader.load(getClass().getResource(path));
        }
    }
}
