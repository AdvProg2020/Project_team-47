package model.send.receive;

import model.others.SpecialProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMessage {
    private String type;
    private ArrayList<String> arrayList;
    private HashMap<String, String> hashMap;
    private ArrayList<SpecialProperty> properties;
    private int firstInt;
    private SpecialProperty property;

    public ClientMessage() {

    }

    public ClientMessage(String type) {
        this.type = type;
    }

    public int getFirstInt() {
        return firstInt;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public ArrayList<SpecialProperty> getProperties() {
        return properties;
    }

    public SpecialProperty getProperty() {
        return property;
    }

    public void setProperty(SpecialProperty property) {
        this.property = property;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
