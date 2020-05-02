package model.others.request;

import model.send.receive.RequestInfo;

public abstract class MainRequest {
    public MainRequest() {
    }

    abstract public void requestInfoSetter(RequestInfo requestInfo);

    abstract void accept(String type);
}
