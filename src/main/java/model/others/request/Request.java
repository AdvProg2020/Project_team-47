package model.others.request;

import com.google.gson.Gson;
import model.others.Date;
import model.send.receive.RequestInfo;
import model.user.User;

import java.util.ArrayList;
import java.util.Random;

public class Request {
    private static ArrayList<Request> allNewRequests;
    private String requestId;
    private User requestSender;
    private String type;
    private MainRequest mainRequest;
    private Date applyDate;
    private Date responseDate;
    private String status;


    public Request() {
        allNewRequests.add(this);
        this.requestId = requestIdCreator();
    }

    private static String requestIdCreator() {
        StringBuilder id = new StringBuilder();
        Random randomNumber = new Random();
        String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        id.append(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())));
        id.append(upperCaseAlphabet.charAt(randomNumber.nextInt(upperCaseAlphabet.length())));
        id.append(randomNumber.nextInt(10000));
        if (isThereRequestWithId(id.toString())) {
            return requestIdCreator();
        } else
            return id.toString();
    }

    public static String allRequestInfo() {
        ArrayList<String> allRequest = new ArrayList<>();
        for (Request newRequest : allNewRequests) {
            allRequest.add(newRequest.getRequestInfo());
        }
        return (new Gson()).toJson(allRequest);
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
    }

    public static void declineNewRequest(String id) {
        Request request = getRequestById(id);
        allNewRequests.remove(request);
    }

    public static void acceptAddOff(Request request) {
    }

    public static ArrayList<Request> getAllNewRequests() {
        return allNewRequests;
    }

    public static void setAllNewRequests(ArrayList<Request> allNewRequests) {
        Request.allNewRequests = allNewRequests;
    }

    public String getRequestInfo() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setApplyDate(applyDate);
        requestInfo.setType(type);
        requestInfo.setId(requestId);
        requestInfo.setRequestedSender(requestSender.getUsername());
        return (new Gson()).toJson(requestInfo);
    }

    public String detail() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setApplyDate(applyDate);
        requestInfo.setId(requestId);
        requestInfo.setType(type);
        requestInfo.setRequestedSender(requestSender.getUsername());
        this.mainRequest.requestInfoSetter(requestInfo);
        return (new Gson()).toJson(requestInfo);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public User getRequestSender() {
        return requestSender;
    }

    public void setRequestSender(User requestSender) {
        this.requestSender = requestSender;
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

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MainRequest getMainRequest() {
        return mainRequest;
    }

    public void setMainRequest(MainRequest mainRequest) {
        this.mainRequest = mainRequest;
    }
}
