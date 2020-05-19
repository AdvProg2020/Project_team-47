package model.send.receive;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMessage {
    private String messageContext;
    private ArrayList<String> messageArrayListInputs;
    private HashMap<String, String> messageFirstHashMapInputs;
    private HashMap<String, String> messageSecondHashMapInputs;
    private String firstString;
    private String secondString;
    private Object object;
    private int firstInt;

    public ClientMessage() {

    }

    public int getFirstInt() {
        return firstInt;
    }

    public void setFirstInt(int firstInt) {
        this.firstInt = firstInt;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public HashMap<String, String> getSecondHashMap() {
        return messageSecondHashMapInputs;
    }

    public void setMessageSecondHashMapInputs(HashMap<String, String> messageSecondHashMapInputs) {
        this.messageSecondHashMapInputs = messageSecondHashMapInputs;
    }

    public String getFirstString() {
        return firstString;
    }

    public void setFirstString(String firstString) {
        this.firstString = firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public void setSecondString(String secondString) {
        this.secondString = secondString;
    }

    public String getRequest() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public ArrayList<String> getArrayList() {
        return messageArrayListInputs;
    }

    public void setMessageArrayListInputs(ArrayList<String> messageArrayListInputs) {
        this.messageArrayListInputs = messageArrayListInputs;
    }

    public HashMap<String, String> getFirstHashMap() {
        return messageFirstHashMapInputs;
    }

    public void setMessageFirstHashMapInputs(HashMap<String, String> messageFirstHashMapInputs) {
        this.messageFirstHashMapInputs = messageFirstHashMapInputs;
    }
}
