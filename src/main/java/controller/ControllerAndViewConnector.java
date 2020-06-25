package controller;

import com.google.gson.Gson;
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
        System.out.println(new Gson().toJson(answer));
        return answer;
    }

}