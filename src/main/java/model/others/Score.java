package model.others;

import model.user.User;

public class Score {
    private User whoSubmitScore;
    private int score;
    private User seller;
    private Product product;


    public Score() {
    }

    public User getWhoSubmitScore() {
        return whoSubmitScore;
    }

    public void setWhoSubmitScore(User whoSubmitScore) {
        this.whoSubmitScore = whoSubmitScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
