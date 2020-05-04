package model.send.receive;

import model.others.Date;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestInfo extends ServerMessage {
    private String id;
    private String requestedSenderUsername;
    private String type;
    private Date applyDate;
    private EditInfo editInfo;
    private AddInfo addInfo;

    public String getRequestedSenderUsername() {
        return requestedSenderUsername;
    }

    public void setRequestedSenderUsername(String requestedSenderUsername) {
        this.requestedSenderUsername = requestedSenderUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestedSender() {
        return requestedSenderUsername;
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

    public Date getApplyDate() {
        return applyDate;
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

        public HashMap<String, String> getNewValueHashMap() {
            return newValueHashMap;
        }

        public void setNewValueHashMap(HashMap<String, String> newValueHashMap) {
            this.newValueHashMap = newValueHashMap;
        }

        public String getChangeField() {
            return changeField;
        }

        public void setChangeField(String changeField) {
            this.changeField = changeField;
        }

        public String getNewValue() {
            return newValue;
        }

        public void setNewValue(String newValue) {
            this.newValue = newValue;
        }

        public ArrayList<String> getNewValueArrayList() {
            return newValueArrayList;
        }

        public void setNewValueArrayList(ArrayList<String> newValueArrayList) {
            this.newValueArrayList = newValueArrayList;
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

        public String getSellerUsername() {
            return sellerUsername;
        }

        public void setSellerUsername(String sellerUsername) {
            this.sellerUsername = sellerUsername;
        }

        public HashMap<String, String> getAddingInformation() {
            return addingInformation;
        }

        public void setAddingInformation(HashMap<String, String> addingInformation) {
            this.addingInformation = addingInformation;
        }
    }

}
