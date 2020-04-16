package model.others;

import model.user.User;

import java.util.ArrayList;

public class Request {
    private static ArrayList<Request> allNewRequests;
    private static int requestIdCounter;
    private int requestId;
    private User requestSender;
    private String type;
    private String request;
    private Date applyDate;
    private Date responseDate;
    private String status;


    public String getRequestInfo(){return null;}



    public static Request getNewRequestById(String id){return null;}




    public static void acceptNewRequest(String id){}
    public static void declineNewRequest(String id){}

    public static void acceptEditProduct(Request request){}
    public static void acceptAddProduct(Request request){}
    public static void acceptRemoveProduct(Request request){}
    public static void acceptEditOff(Request request){}
    public static void acceptAddOff(Request request){}



    public static ArrayList<Request> getAllNewRequests() {
        return allNewRequests;
    }

    public static int getRequestIdCounter() {
        return requestIdCounter;
    }

    public int getRequestId() {
        return requestId;
    }

    public User getRequestSender() {
        return requestSender;
    }

    public String getType() {
        return type;
    }

    public String getRequest() {
        return request;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public String getStatus() {
        return status;
    }

    public static void setAllNewRequests(ArrayList<Request> allNewRequests) {
        Request.allNewRequests = allNewRequests;
    }

    public static void setRequestIdCounter(int requestIdCounter) {
        Request.requestIdCounter = requestIdCounter;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setRequestSender(User requestSender) {
        this.requestSender = requestSender;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request() {
    }
}
