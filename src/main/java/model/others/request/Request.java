package model.others.request;

import controller.Controller;
import database.Database;
import database.RequestData;
import model.ecxeption.request.RequestDoesntExistException;
import model.others.Sort;
import model.send.receive.RequestInfo;
import model.user.User;

import java.util.ArrayList;
import java.util.Date;

public class Request {
    private static ArrayList<Request> allNewRequests;

    static {
        allNewRequests = new ArrayList<>();
    }

    private String requestId;
    private String requestSenderUserName;
    private String type;
    private MainRequest mainRequest;
    private Date applyDate;
    private Date responseDate;
    private String status;

    public Request() {
        applyDate = Controller.getCurrentTime();
        allNewRequests.add(this);
        this.requestId = requestIdCreator();
    }

    private static String requestIdCreator() {
        String id = Controller.idCreator();
        if (isThereRequestWithId(id)) {
            return requestIdCreator();
        } else
            return id;
    }

    public static ArrayList<RequestInfo> allRequestInfo(String sortField, String sortDirection) {
        ArrayList<Request> newRequests = Sort.sortRequest(sortField, sortDirection, allNewRequests);
        ArrayList<RequestInfo> allRequest = new ArrayList<>();
        for (Request newRequest : newRequests) {
            allRequest.add(newRequest.getRequestInfo());
        }
        return allRequest;
    }

    public static boolean isThereRequestWithId(String id) {
        for (Request request : allNewRequests) {
            if (request == null) continue;
            if (id.equalsIgnoreCase(request.requestId)) {
                return true;
            }
        }
        return false;
    }

    public static Request getRequestById(String requestId) throws RequestDoesntExistException {
        for (Request request : allNewRequests) {
            if (request.requestId.equalsIgnoreCase(requestId)) {
                return request;
            }
        }
        throw new RequestDoesntExistException();
    }

    public static void declineNewRequest(String id) throws RequestDoesntExistException {
        Request request = getRequestById(id);
        request.mainRequest.decline();
        allNewRequests.remove(request);
        request.removeFromDatabase();
    }

    public static void setAllNewRequests(ArrayList<Request> allNewRequests) {
        if (allNewRequests == null)
            return;
        Request.allNewRequests = allNewRequests;
    }

    private void mainRequest() {

    }

    public void accept() {
        this.mainRequest.accept();
        allNewRequests.remove(this);
        this.removeFromDatabase();
    }

    public void decline() {
        this.mainRequest.decline();
        allNewRequests.remove(this);
        this.removeFromDatabase();
    }

    public boolean canDoRequest() {
        return this.mainRequest.update();
    }

    public void addToDatabase() {
        RequestData requestData = new RequestData(requestId, requestSenderUserName, type, applyDate, responseDate, status);
        if (mainRequest instanceof AddCommentRequest) requestData.setAddCommentRequest((AddCommentRequest) mainRequest);
        else if (mainRequest instanceof AddOffRequest) requestData.setAddOffRequest((AddOffRequest) mainRequest);
        else if (mainRequest instanceof AddProductRequest)
            requestData.setAddProductRequest((AddProductRequest) mainRequest);
        else if (mainRequest instanceof EditOffRequest) requestData.setEditOffRequest((EditOffRequest) mainRequest);
        else if (mainRequest instanceof EditProductRequest)
            requestData.setEditProductRequest((EditProductRequest) mainRequest);
        Database.addRequestToDatabase(requestData, requestId);
    }

    public RequestInfo getRequestInfo() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setApplyDate(applyDate);
        requestInfo.setType(type);
        requestInfo.setId(requestId);
        requestInfo.setRequestedSender(requestSenderUserName);
        this.mainRequest.requestInfoSetter(requestInfo);
        return requestInfo;
    }

    public RequestInfo detail() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setApplyDate(applyDate);
        requestInfo.setId(requestId);
        requestInfo.setType(type);
        requestInfo.setRequestedSender(requestSenderUserName);
        this.mainRequest.requestInfoSetter(requestInfo);
        return requestInfo;
    }

    public String getRequestSender() {
        return requestSenderUserName;
    }

    public void setRequestSender(User requestSender) {
        this.requestSenderUserName = requestSender.getUsername();
    }

    public void setRequestSender(String requestSenderUserName) {
        this.requestSenderUserName = requestSenderUserName;
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

    public void setMainRequest(MainRequest mainRequest) {
        this.mainRequest = mainRequest;
    }

    public void removeFromDatabase() {
        Database.removeRequest(this.requestId);
    }

    public void setId(String requestId) {
        this.requestId = requestId;
    }

    public void setRequestSenderUserName(String requestSenderUserName) {
        this.requestSenderUserName = requestSenderUserName;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
