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
    private byte[] file;
    private String fileExtension;
    public ClientMessage() {

    }

    public void setProperties(ArrayList<SpecialProperty> properties) {
        this.properties = properties;
    }

    public ClientMessage(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
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

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
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
}
