package model.send.receive;

import model.user.User;

import java.util.ArrayList;
import java.util.Date;

public class DiscountCodeInfo {
    private final Date startTime;
    private final Date finishTime;
    private final int percent;
    private String code;
    private int maxUsableTime;
    private int maxDiscountAmount;
    private final ArrayList<String> usersAbleToUse;

    public DiscountCodeInfo(Date startTime, Date finishTime, int percent) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.percent = percent;
        this.usersAbleToUse = new ArrayList<>();
    }

    public void addUser(User user) {
        this.usersAbleToUse.add(user.getUsername());
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public int getPercent() {
        return percent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxUsableTime() {
        return maxUsableTime;
    }

    public void setMaxUsableTime(int maxUsableTime) {
        this.maxUsableTime = maxUsableTime;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public ArrayList<String> getUsersAbleToUse() {
        return usersAbleToUse;
    }
}
