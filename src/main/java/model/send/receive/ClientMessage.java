package model.send.receive;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMessage extends Message {
    private String messageContext;
    private ArrayList<String> messageArrayListInputs;
    private HashMap<String, String> messageFirstHashMapInputs;
    private HashMap<String, String> messageSecondHashMapInputs;
    private String firstString;
    private String secondString;

    public ClientMessage() {

    }

    public HashMap<String, String> getMessageSecondHashMapInputs() {
        return messageSecondHashMapInputs;
    }

    public void setMessageSecondHashMapInputs(HashMap<String, String> messageSecondHashMapInputs) {
        this.messageSecondHashMapInputs = messageSecondHashMapInputs;
    }

    public String getFirstString() {
        return firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public void setSecondString(String secondString) {
        this.secondString = secondString;
    }

    public void setFirstString(String firstString) {
        this.firstString = firstString;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public ArrayList<String> getMessageArrayListInputs() {
        return messageArrayListInputs;
    }

    public void setMessageArrayListInputs(ArrayList<String> messageArrayListInputs) {
        this.messageArrayListInputs = messageArrayListInputs;
    }

    public HashMap<String, String> getMessageFirstHashMapInputs() {
        return messageFirstHashMapInputs;
    }

    public void setMessageFirstHashMapInputs(HashMap<String, String> messageFirstHashMapInputs) {
        this.messageFirstHashMapInputs = messageFirstHashMapInputs;
    }
}
