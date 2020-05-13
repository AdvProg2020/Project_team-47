package model.send.receive;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientMessage extends Message {
    private String messageContext;
    private ArrayList<String> messageInputs;

    public ClientMessage(String messageContext) {
        this.messageContext = messageContext;
        this.messageInputs = new ArrayList<>();
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public ArrayList<String> getMessageInputs() {
        return messageInputs;
    }

    public void setMessageInputs(ArrayList<String> messageInputs) {
        this.messageInputs = messageInputs;
    }
}
