package model.bank;

import controller.Controller;

import java.util.Calendar;
import java.util.Date;

public class Token {
    private Date startTime;
    private Date finishTime;
    private String username;
    private String password;
    private int tokenId;

    public Token(String username, String password) {
        this.username = username;
        this.password = password;
        setStartAndFinish();
        Bank.getInstance().getTokens().add(this);
        tokenId = Bank.getInstance().getTokens().size();
    }

    private void setStartAndFinish() {
        this.startTime = Controller.getCurrentTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        this.finishTime = cal.getTime();
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getTokenId() {
        return tokenId;
    }
}
