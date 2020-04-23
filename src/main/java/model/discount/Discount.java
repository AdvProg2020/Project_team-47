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

    public Date getDiscountStartTime() {
        return discountStartTime;
    }

    public Date getDiscountFinishTime() {
        return discountFinishTime;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }



    abstract public String discountInfoForSending();
}
