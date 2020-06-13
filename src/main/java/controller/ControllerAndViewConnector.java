package controller;

import model.ecxeption.Exception;
import model.send.receive.ClientMessage;
import model.send.receive.ServerMessage;


public class ControllerAndViewConnector {

    public static ServerMessage commandProcess(ClientMessage request) {
        ServerMessage answer;
        try {
            answer = Controller.process(request);
        } catch (Exception e) {
            answer = new ServerMessage("Error", e.getMessage());
        }
        return answer;
    }

}