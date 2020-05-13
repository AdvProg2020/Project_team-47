package view;

import com.google.gson.Gson;
import controller.ControllerAndViewConnector;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

public class ViewToController {
    public static void getUserType() {

    }

    public static void sendControllerMessage(String messageContext) {
        ClientMessage clientMessage = new ClientMessage(messageContext);
        ControllerAndViewConnector.setClientMessage((new Gson()).toJson(clientMessage));
        ControllerAndViewConnector.commandProcess();

    }
    public static void editField(String field) {
        //todo
    }
    public static void goToProductPage(String productId) {
        sendControllerMessage("go to product page");
    }
}
