package model.send.receive;


import model.others.ClientFilter;
import model.others.Comment;

import java.util.ArrayList;

public class ServerMessage {
    private String type;
    private String errorMessage;
    private String firstString;
    private String secondString;
    private double number;
    private CategoryInfo categoryInfo;
    private DiscountCodeInfo discountCodeInfo;
    private LogInfo logInfo;
    private OffInfo offInfo;
    private ProductInfo productInfo;
    private RequestInfo requestInfo;
    private UserInfo userInfo;
    private ArrayList<String> strings;
    private ArrayList<CategoryInfo> categoryInfoArrayList;
    private ArrayList<Comment> commentArrayList;
    private ArrayList<DiscountCodeInfo> discountCodeInfoArrayList;
    private ArrayList<LogInfo> logInfoArrayList;
    private ArrayList<OffInfo> offInfoArrayList;
    private ArrayList<ProductInfo> productInfoArrayList;
    private ArrayList<RequestInfo> requestArrayList;
    private ArrayList<UserInfo> userInfoArrayList;
    private CartInfo cartInfo;
    private ArrayList<ClientFilter> filters;
    private String id;

    public ServerMessage() {
    }

    public ServerMessage(String error, String errorMessage) {
        this.type = error;
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ClientFilter> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<ClientFilter> filters) {
        this.filters = filters;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CartInfo getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(CartInfo cartInfo) {
        this.cartInfo = cartInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstString() {
        return firstString;
    }

    public void setFirstString(String firstString) {
        this.firstString = firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public void setSecondString(String secondString) {
        this.secondString = secondString;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public void setDiscountCodeInfo(DiscountCodeInfo discountCodeInfo) {
        this.discountCodeInfo = discountCodeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerMessage)) return false;

        ServerMessage that = (ServerMessage) o;

        if (!type.equals(that.type)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    public OffInfo getOffInfo() {
        return offInfo;
    }

    public void setOffInfo(OffInfo offInfo) {
        this.offInfo = offInfo;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }

    public ArrayList<CategoryInfo> getCategoryInfoArrayList() {
        return categoryInfoArrayList;
    }

    public void setCategoryInfoArrayList(ArrayList<CategoryInfo> categoryInfoArrayList) {
        this.categoryInfoArrayList = categoryInfoArrayList;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

    public ArrayList<DiscountCodeInfo> getDiscountCodeInfoArrayList() {
        return discountCodeInfoArrayList;
    }

    public void setDiscountCodeInfoArrayList(ArrayList<DiscountCodeInfo> discountCodeInfoArrayList) {
        this.discountCodeInfoArrayList = discountCodeInfoArrayList;
    }

    public ArrayList<LogInfo> getLogInfoArrayList() {
        return logInfoArrayList;
    }

    public void setLogInfoArrayList(ArrayList<LogInfo> logInfoArrayList) {
        this.logInfoArrayList = logInfoArrayList;
    }

    public ArrayList<OffInfo> getOffInfoArrayList() {
        return offInfoArrayList;
    }

    public void setOffInfoArrayList(ArrayList<OffInfo> offInfoArrayList) {
        this.offInfoArrayList = offInfoArrayList;
    }

    public ArrayList<ProductInfo> getProductInfoArrayList() {
        return productInfoArrayList;
    }

    public void setProductInfoArrayList(ArrayList<ProductInfo> productInfoArrayList) {
        this.productInfoArrayList = productInfoArrayList;
    }

    public ArrayList<RequestInfo> getRequestArrayList() {
        return requestArrayList;
    }

    public void setRequestArrayList(ArrayList<RequestInfo> requestArrayList) {
        this.requestArrayList = requestArrayList;
    }

    public ArrayList<UserInfo> getUserInfoArrayList() {
        return userInfoArrayList;
    }

    public void setUserInfoArrayList(ArrayList<UserInfo> userInfoArrayList) {
        this.userInfoArrayList = userInfoArrayList;
    }
}
