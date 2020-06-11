package model.others.request;

import model.send.receive.RequestInfo;

public abstract class MainRequest {
    public MainRequest() {
    }

    public abstract void requestInfoSetter(RequestInfo requestInfo);

    abstract void accept();

    abstract boolean update();

    public abstract void decline();
}
