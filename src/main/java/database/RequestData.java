package database;

import model.others.request.*;

import java.util.Date;

public class RequestData {
    private final String requestId;
    private final String requestSenderUserName;
    private final String type;
    private final Date applyDate;
    private final Date responseDate;
    private final String status;
    private AddCommentRequest addCommentRequest;
    private AddOffRequest addOffRequest;
    private AddProductRequest addProductRequest;
    private EditOffRequest editOffRequest;
    private EditProductRequest editProductRequest;

    public RequestData(String requestId, String requestSenderUserName, String type, Date applyDate, Date responseDate, String status) {
        this.requestId = requestId;
        this.requestSenderUserName = requestSenderUserName;
        this.type = type;
        this.applyDate = applyDate;
        this.responseDate = responseDate;
        this.status = status;
    }

    public Request getRequest() {
        Request request = new Request();
        request.setId(this.requestId);
        request.setRequestSenderUserName(this.requestSenderUserName);
        request.setType(this.type);
        request.setApplyDate(this.applyDate);
        request.setResponseDate(this.responseDate);
        request.setStatus(this.status);
        if (addCommentRequest != null) request.setMainRequest(addCommentRequest);
        else if (addOffRequest != null) request.setMainRequest(addOffRequest);
        else if (addProductRequest != null) request.setMainRequest(addProductRequest);
        else if (editOffRequest != null) request.setMainRequest(editOffRequest);
        else if (editProductRequest != null) request.setMainRequest(editProductRequest);
        return request;
    }

    public void setAddCommentRequest(AddCommentRequest addCommentRequest) {
        this.addCommentRequest = addCommentRequest;
    }

    public void setAddOffRequest(AddOffRequest addOffRequest) {
        this.addOffRequest = addOffRequest;
    }

    public void setAddProductRequest(AddProductRequest addProductRequest) {
        this.addProductRequest = addProductRequest;
    }

    public void setEditOffRequest(EditOffRequest editOffRequest) {
        this.editOffRequest = editOffRequest;
    }

    public void setEditProductRequest(EditProductRequest editProductRequest) {
        this.editProductRequest = editProductRequest;
    }

    public void addToRequests() {

    }


}
