package model.discount;

import model.others.Date;

abstract public class Discount {
    protected Date discountStartTime;
    protected Date discountFinishTime;
    protected int discountPercent;

    public Discount(Date discountStartTime, Date discountFinishTime, int discountPercent) {
        this.discountStartTime = discountStartTime;
        this.discountFinishTime = discountFinishTime;
        this.discountPercent = discountPercent;
    }

    public Discount() {}

    public Date getDiscountStartTime() {
        return discountStartTime;
    }

    public void setDiscountStartTime(Date discountStartTime) {
        this.discountStartTime = discountStartTime;
    }

    public Date getDiscountFinishTime() {
        return discountFinishTime;
    }

    public void setDiscountFinishTime(Date discountFinishTime) {
        this.discountFinishTime = discountFinishTime;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    abstract public String discountInfoForSending();
}
