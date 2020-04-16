package model.discount;

import model.others.Date;

abstract public class Discount {
    protected Date discountStartTime;
    protected Date discountFinishTime;
    protected int discountPercent;

    public Discount() {
    }

    abstract public String discountInfoForSending();
}
