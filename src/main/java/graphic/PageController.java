package graphic;

import controller.ControllerAndViewConnector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public abstract class PageController implements Initializable {

    protected static Scene getScene(String path) {
        return FxmlLoaderClass.getScene(path);
    }

    public static Image byteToImage(byte[] imageBytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage bImage = ImageIO.read(bis);
            File file = File.createTempFile("image", ".jpg");
            ImageIO.write(bImage, "jpg", file);
            return new Image(file.toURI().toString());
        } catch (IOException ignored) {
        }
        return null;
    }

    public static byte[] imageToByte(File image) {
        try {
            FileInputStream fileInputStream = new FileInputStream(image);
            return new ByteArrayInputStream(fileInputStream.readAllBytes()).readAllBytes();
        } catch (IOException ignored) {
            return null;
        }
    }

    public ServerMessage send(ClientMessage request) {
        return ControllerAndViewConnector.commandProcess(request);
    }

    //this function will use to clear scenes
    public abstract void clearPage();

    //this function will update scene objects
    public abstract void update();

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
