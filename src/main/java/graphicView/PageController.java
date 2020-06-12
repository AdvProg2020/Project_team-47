package graphicView;

import controller.ControllerAndViewConnector;
import javafx.fxml.Initializable;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

public abstract class PageController implements Initializable {

    public ServerMessage send(ClientMessage request) {
        return ControllerAndViewConnector.commandProcess(request);
    }

    //this function will use to clear scenes
    public abstract void clearPage();

    //this function will update scene objects
    public abstract void update();
}
