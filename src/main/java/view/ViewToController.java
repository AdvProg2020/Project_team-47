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
    private static HashMap<String, String> viewMessageFirstHashMapInputs;
    private static HashMap<String, String> viewMessageSecondHashMapInputs;
    private static String firstString;
    private static String secondString;
    private static Object viewMessageObject;

    static {
        viewMessageArrayListInputs = new ArrayList<>();
        serverMessage = new ServerMessage();
        viewMessageFirstHashMapInputs = new HashMap<>();
        viewMessageSecondHashMapInputs = new HashMap<>();
        viewMessageObject = new Object();
    }

    public static Object getViewMessageObject() {
        return viewMessageObject;
    }

    public static void setViewMessageObject(Object viewMessageObject) {
        ViewToController.viewMessageObject = viewMessageObject;
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

    public static void setServerMessage(ServerMessage serverMessage) {
        ViewToController.serverMessage = serverMessage;
    }

    public static HashMap<String, String> getViewMessageFirstHashMapInputs() {
        return viewMessageFirstHashMapInputs;
    }

    public static void setViewMessageFirstHashMapInputs(HashMap<String, String> viewMessageFirstHashMapInputs) {
        ViewToController.viewMessageFirstHashMapInputs = viewMessageFirstHashMapInputs;
    }

    public static String getFirstString() {
        return firstString;
    }

    public static void setFirstString(String firstString) {
        ViewToController.firstString = firstString;
    }

    public static String getSecondString() {
        return secondString;
    }

    public static void setSecondString(String secondString) {
        ViewToController.secondString = secondString;
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

    public static HashMap<String, String> getViewMessageSecondHashMapInputs() {
        return viewMessageSecondHashMapInputs;
    }

    public static void setViewMessageSecondHashMapInputs(HashMap<String, String> viewMessageSecondHashMapInputs) {
        ViewToController.viewMessageSecondHashMapInputs = viewMessageSecondHashMapInputs;
    }

    public static void sendMessageToController() {
        ClientMessage clientMessage = new ClientMessage();

        clientMessage.setMessageContext(viewMessage);
        clientMessage.setMessageArrayListInputs(viewMessageArrayListInputs);
        clientMessage.setMessageFirstHashMapInputs(viewMessageFirstHashMapInputs);

        clientMessage.setMessageSecondHashMapInputs(viewMessageSecondHashMapInputs);

        clientMessage.setFirstString(firstString);
        clientMessage.setSecondString(secondString);

        clientMessage.setObject(viewMessageObject);

        ControllerAndViewConnector.setClientMessage((new Gson()).toJson(clientMessage));
        ControllerAndViewConnector.commandProcess();
    }

}
