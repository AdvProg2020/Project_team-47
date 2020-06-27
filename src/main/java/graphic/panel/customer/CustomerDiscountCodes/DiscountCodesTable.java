package graphic.panel.customer.CustomerDiscountCodes;

import java.util.Date;

public class DiscountCodesTable {
    private final Date startTime;
    private final Date finishTime;
    private final int percent;
    private final String code;
    private final int maxUsableTime;
    private final int maxDiscountAmount;

    public DiscountCodesTable(Date startTime, Date finishTime, int percent,
                              String code, int maxUsableTime, int maxDiscountAmount) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.percent = percent;
        this.code = code;
        this.maxUsableTime = maxUsableTime;
        this.maxDiscountAmount = maxDiscountAmount;
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

    public int getMaxUsableTime() {
        return maxUsableTime;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
}
