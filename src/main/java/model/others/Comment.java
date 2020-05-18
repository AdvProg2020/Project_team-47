package model.others;

public class Comment {

    private String whoComment;
    private String productId;
    private String productName;
    private String sellerCommentBelongTo;
    private String commentText;
    private String commentTitle;
    private boolean doesCustomerBought;
    private String status;

    public Comment() {
        this.status = "IN_CONFIRM_QUEUE";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDoesCustomerBought() {
        return doesCustomerBought;
    }

    public String getStatus() {
        return status;
    }

    public String getWhoComment() {
        return whoComment;
    }

    public void setWhoComment(String whoComment) {
        this.whoComment = whoComment;
    }

    public String getSellerCommentBelongTo() {
        return sellerCommentBelongTo;
    }

    public void setSellerCommentBelongTo(String sellerCommentBelongTo) {
        this.sellerCommentBelongTo = sellerCommentBelongTo;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public boolean doesCustomerBought() {
        return doesCustomerBought;
    }

    public void setDoesCustomerBought(boolean doesCustomerBought) {
        this.doesCustomerBought = doesCustomerBought;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Comment{}";
    }
}
