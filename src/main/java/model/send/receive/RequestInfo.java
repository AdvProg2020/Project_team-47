package model.send.receive;

import model.others.Date;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestInfo {
    private String id;
    private String requestedSenderUsername;
    private String type;
    private Date applyDate;
    private EditInfo editInfo;
    private AddInfo addInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRequestedSender(String requestedSender) {
        this.requestedSenderUsername = requestedSender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public void setEditInfo(String filed, String newValue, ArrayList<String> newValueArrayList) {
        this.editInfo = new EditInfo("edit-off", filed, newValue, newValueArrayList);
    }

    public void setEditInfo(String filed, String newValue, HashMap<String, String> newValueArrayList) {
        this.editInfo = new EditInfo("edit-product", filed, newValue, newValueArrayList);
    }


    public void setAddInfo(String type, String username, HashMap<String, String> addingInfo) {
        this.addInfo = new AddInfo(type, username, addingInfo);
    }


    private static class EditInfo {
        private String editType;
        private String changeField;
        private String newValue;
        private ArrayList<String> newValueArrayList;
        private HashMap<String, String> newValueHashMap;

        public EditInfo(String editType, String changeField, String newValue, ArrayList<String> newValueArrayList) {
            this.editType = editType;
            this.changeField = changeField;
            this.newValue = newValue;
            this.newValueArrayList = newValueArrayList;
        }

        public EditInfo(String editType, String changeField, String newValue, HashMap<String, String> newValueHashMap) {
            this.editType = editType;
            this.changeField = changeField;
            this.newValue = newValue;
            this.newValueHashMap = newValueHashMap;
        }
    }

    private static class AddInfo {
        String sellerUsername;
        HashMap<String, String> addingInformation;
        String type;

        public AddInfo(String type, String sellerUsername, HashMap<String, String> addingInformation) {
            this.sellerUsername = sellerUsername;
            this.addingInformation = addingInformation;
        }

    }

}
