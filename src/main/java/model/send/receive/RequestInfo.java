package model.send.receive;

import model.others.Comment;
import model.others.SpecialProperty;

import java.util.ArrayList;
import java.util.Date;

public class RequestInfo {
    private String id;
    private String requestedSenderUsername;
    private String type;
    private Date applyDate;
    private EditInfo editInfo;
    private AddInfo addInfo;

    @Override
    public String toString() {
        if (editInfo == null)
            return "Id: " + id +
                    "\nWho Send: " + requestedSenderUsername +
                    "\nType: " + type + "\n" +
                    addInfo.toString();
        if (addInfo == null)
            return "Id: " + id +
                    "\nWho Send: " + requestedSenderUsername +
                    "\nType: " + type + "\n" +
                    editInfo.toString();
        return "Id: " + id +
                "\nWho Send: " + requestedSenderUsername +
                "\nType: " + type;
    }

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

    public void setEditInfo(String field, String newValue, String changeType, String id) {
        this.editInfo = new EditInfo("edit-off", field, newValue, changeType, id);
    }

    public void setEditInfo(String field, String newValue, String changeType, String id, SpecialProperty property) {
        this.editInfo = new EditInfo("edit-product", field, newValue, changeType, id, property);
    }

    public void setAddInfo(String type, String username, ArrayList<String> addingInfo) {
        this.addInfo = new AddInfo(type, username, addingInfo);
    }

    public void setAddComment(Comment comment) {
        this.addInfo = new AddInfo(comment);
        this.addInfo.type = "add-comment";
    }

    public String getRequestedSenderUsername() {
        return requestedSenderUsername;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public EditInfo getEditInfo() {
        return editInfo;
    }

    public AddInfo getAddInfo() {
        return addInfo;
    }

    private static class EditInfo {
        private final String editType;
        private final String changeField;
        private final String newValue;
        private String changeType;
        private String id;
        private SpecialProperty property;

        public EditInfo(String editType, String changeField, String newValue, String changeType, String id) {
            this.editType = editType;
            this.changeField = changeField;
            this.newValue = newValue;
            this.changeType = changeType;
            this.id = id;
        }

        public EditInfo(String editType, String changeField, String newValue) {
            this.editType = editType;
            this.changeField = changeField;
            this.newValue = newValue;
        }

        public EditInfo(String editType, String changeField, String newValue, String changeType, String id, SpecialProperty property) {
            this.changeField = changeField;
            this.newValue = newValue;
            this.changeType = changeType;
            this.editType = editType;
            this.id = id;
            this.property = property;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Edit Info:");
            if (editType != null) stringBuilder.append("\nEdit Type: ").append(editType);
            if (changeField != null) stringBuilder.append("\nChange Field: ").append(changeField);
            if (newValue != null) stringBuilder.append("\nNew Value: ").append(newValue);
            if (changeType != null) stringBuilder.append("\nChange Type: ").append(changeType);
            if (property != null&&property.getType()!=null&&property.getKey()!=null)
                stringBuilder.append("\nSpecial Property: ").append(property.toString());
            return stringBuilder.toString();
        }
    }

    private static class AddInfo {
        String sellerUsername;
        ArrayList<String> addingInformation;
        String type;
        Comment comment;

        public AddInfo(Comment comment) {
            this.comment = comment;
        }

        public AddInfo(String type, String sellerUsername, ArrayList<String> addingInformation) {
            this.sellerUsername = sellerUsername;
            this.addingInformation = addingInformation;
            this.type = type;
        }

        @Override
        public String toString() {
            if (comment != null) {
                return "Add Info:\n" + comment.toString();
            } else {
                StringBuilder stringBuilder = new StringBuilder("AddInfo:\n" +
                        "Seller Username: " + sellerUsername +
                        "\nType: " + type + "\n");
                for (String info : addingInformation) {
                    stringBuilder.append(info).append("\n");
                }
                return stringBuilder.toString();
            }
        }
    }

}
