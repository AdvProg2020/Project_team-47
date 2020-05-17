package model.discount;

import java.util.Date;

abstract public class Discount {
    protected Date startTime;
    protected Date finishTime;
    protected int percent;

    public Discount(Date startTime, Date finishTime, int percent) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.percent = percent;
    }

    public Discount() {
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public abstract void removeFromDatabase();
}
