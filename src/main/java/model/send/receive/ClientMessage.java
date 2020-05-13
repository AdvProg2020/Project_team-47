package model.send.receive;

public class ClientMessage extends Message {
    private String messageContext;

    public ClientMessage(String messageContext) {
        this.messageContext = messageContext;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

}
