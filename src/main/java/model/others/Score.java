package model.others;

public class Score {
    private String whoSubmitScore;
    private int score;
    private String productId;


    public Score() {
    }

    public String getWhoSubmitScore() {
        return whoSubmitScore;
    }

    public void setWhoSubmitScore(String whoSubmitScore) {
        this.whoSubmitScore = whoSubmitScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProduct() {
        return productId;
    }

    public void setProduct(String productId) {
        this.productId = productId;
    }
}
