package controller;

import model.send.receive.ClientMessage;

import java.util.HashMap;

public class ControllerAndViewConnector {
    private static String clientMessage;
    private static String serverAnswer;
    private static HashMap<String, String> commandRegexHashMap;

    public static String getClientMessage() {
        return clientMessage;
    }

    public static void setClientMessage(String clientMessage) {
        ControllerAndViewConnector.clientMessage = clientMessage;
    }

    public static String getServerAnswer() {
        return serverAnswer;
    }

    public static void setServerAnswer(String serverAnswer) {
        ControllerAndViewConnector.serverAnswer = serverAnswer;
    }

    public static void sendAnswer(String type, String answer) {

    }


    public static String getAnswer() {
        //unsure
        return serverAnswer;
    }

    public static void commandProcess() {
        ClientMessage request = Controller.getGson().fromJson(clientMessage, ClientMessage.class);
        Controller.process(request);
    }

    public static void generalCommandProcess(ClientMessage message) {
    }
}