package model.others;

import model.user.User;



public class Comment {

    private User whoComment;
    private Product productCommentBelongTo;
    private User sellerCommentBelongTo;
    private String commentText;
    private String commentTitle;
    private boolean doesCustomerBought;

    public Comment() {
    }

    public User getWhoComment() {
        return whoComment;
    }

    public Product getProductCommentBelongTo() {
        return productCommentBelongTo;
    }

    public User getSellerCommentBelongTo() {
        return sellerCommentBelongTo;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public boolean isDoesCustomerBought() {
        return doesCustomerBought;
    }

    public void setWhoComment(User whoComment) {
        this.whoComment = whoComment;
    }

    public void setProductCommentBelongTo(Product productCommentBelongTo) {
        this.productCommentBelongTo = productCommentBelongTo;
    }

    public void setSellerCommentBelongTo(User sellerCommentBelongTo) {
        this.sellerCommentBelongTo = sellerCommentBelongTo;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public void setDoesCustomerBought(boolean doesCustomerBought) {
        this.doesCustomerBought = doesCustomerBought;
    }

    public String getCommentInfoForSending(){return null;}

    @Override
    public String toString() {
        return "Comment{}";
    }
}
