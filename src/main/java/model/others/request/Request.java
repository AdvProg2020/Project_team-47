package model.others.request;

import controller.Controller;
import database.Database;
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
            if (request.requestId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static Request getRequestById(String requestId) {
        for (Request request : allNewRequests) {
            if (request.requestId.equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    public static void acceptNewRequest(String id) {
        Request request = getRequestById(id);
        assert request != null;
        request.mainRequest.accept(request.type);
        allNewRequests.remove(request);
        request.removeFromDatabase();
    }

    public static void declineNewRequest(String id) {
        Request request = getRequestById(id);
        request.mainRequest.decline();
        allNewRequests.remove(request);
        request.removeFromDatabase();
    }

    public static boolean canDoRequest(String id) {
        Request request = getRequestById(id);
        return request.mainRequest.update(request.type);
    }

    public static void setAllNewRequests(ArrayList<Request> allNewRequests) {
        Request.allNewRequests = allNewRequests;
    }

    public void addToDatabase() {
        Database.addRequestToDatabase(this, requestId);
    }

    public RequestInfo getRequestInfo() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setApplyDate(applyDate);
        requestInfo.setType(type);
        requestInfo.setId(requestId);
        requestInfo.setRequestedSender(requestSenderUserName);
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

    public void setMainRequest(MainRequest mainRequest) {
        this.mainRequest = mainRequest;
    }

    public void removeFromDatabase() {
        Database.removeRequest(this.requestId);
    }
}
