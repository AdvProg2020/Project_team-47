package view;

import com.google.gson.Gson;
import controller.ControllerAndViewConnector;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewToController {
    private static String viewMessage;
    private static ArrayList<String> viewMessageArrayListInputs;
    private static String controllerAnswer;
    private static ServerMessage serverMessage;
    private static HashMap<String, String> viewMessageHashMapInputs;

    static {
        viewMessageArrayListInputs = new ArrayList<>();
        serverMessage = new ServerMessage();
        viewMessageHashMapInputs = new HashMap<>();
    }

    public static ArrayList<String> getViewMessageArrayListInputs() {
        return viewMessageArrayListInputs;
    }

    public static void setViewMessageArrayListInputs(ArrayList<String> viewMessageArrayListInputs) {
        ViewToController.viewMessageArrayListInputs = viewMessageArrayListInputs;
    }

    public static ServerMessage getServerMessage() {
        return serverMessage;
    }

    public static HashMap<String, String> getViewMessageHashMapInputs() {
        return viewMessageHashMapInputs;
    }

    public static void setViewMessageHashMapInputs(HashMap<String, String> viewMessageHashMapInputs) {
        ViewToController.viewMessageHashMapInputs = viewMessageHashMapInputs;
    }

    public static void setServerMessage(ServerMessage serverMessage) {
        ViewToController.serverMessage = serverMessage;
    }

    public static String getControllerAnswer() {
        return controllerAnswer;
    }

    public static void setControllerAnswer(String controllerAnswer) {
        ViewToController.controllerAnswer = controllerAnswer;
        ViewToController.serverMessage = (new Gson()).fromJson(controllerAnswer, ServerMessage.class);
    }

    public static void getUserType() {

    }

    public static String getViewMessage() {
        return viewMessage;
    }

    public static void setViewMessage(String viewMessage) {
        ViewToController.viewMessage = viewMessage;
    }

    public static void processControllerAnswer(String viewMessageContext) {
        ServerMessage serverMessage = (new Gson()).fromJson(controllerAnswer, ServerMessage.class);

    }

    public static void sendMessageToController() {
        ClientMessage clientMessage = new ClientMessage(viewMessage, viewMessageArrayListInputs, viewMessageHashMapInputs);
        ControllerAndViewConnector.setClientMessage((new Gson()).toJson(clientMessage));
        ControllerAndViewConnector.commandProcess();
    }

    public static void editField(String field) {
        //todo
    }

}
