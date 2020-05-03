package model.other;

import model.user.User;

public class Score {
    private int score;
    private User buyer;
    private User seller;

    /**
     * constructor may get only buyer and seller
     * because it wouldn't happen to change these two
     * but score maybe changes
     * 
     * @param buyer     person who rates
     * @param seller    one kind of product may has many vendors
     *                  so we differ sellers from each other.
     *                  each seller has a unique rating for a product type
     */
    public Score(User buyer, User seller) {
        this.buyer = buyer;
        this.seller = seller;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }
}
