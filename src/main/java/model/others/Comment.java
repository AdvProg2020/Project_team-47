package model.others;

import java.time.LocalDateTime;
import model.user.User;

public class Comment {
    public static final int WAITING = 0;
    public static final int CONFIRMED = 1;
    public static final int DIMISSED = -1;
    private User commenter;
    private User seller;
    private String title;
    private String explanation;
    private boolean hasBought;
    private LocalDateTime date;
    private int status;

    public Comment(User commenter, User seller, String title, String explanation, boolean hasBought) {
        this.commenter = commenter;
        this.seller = seller;
        this.title = title;
        this.explanation = explanation;
        this.hasBought = hasBought;
        date = LocalDateTime.now();
        status = Comment.WAITING;
    }

    public User getCommenter() {
        return commenter;
    }

    public User getSeller() {
        return seller;
    }

    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean getHasBought() {
        return hasBought;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
