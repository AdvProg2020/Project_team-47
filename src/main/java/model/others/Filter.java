package model.others;

public class Filter {
    private String type;
    private String filterKey;
    private int firstInt;
    private int secondInt;
    private double firstDouble;
    private double secondDouble;
    private String firstFilterValue;
    private String secondFilterValue;

    public double getSecondDouble() {
        return secondDouble;
    }

    public void setSecondDouble(double secondDouble) {
        this.secondDouble = secondDouble;
    }

    public double getFirstDouble() {
        return firstDouble;
    }

    public void setFirstDouble(double firstDouble) {
        this.firstDouble = firstDouble;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFirstInt() {
        return firstInt;
    }

    public void setFirstInt(int firstInt) {
        this.firstInt = firstInt;
    }

    public int getSecondInt() {
        return secondInt;
    }

    public void setSecondInt(int secondInt) {
        this.secondInt = secondInt;
    }

    public String getFirstFilterValue() {
        return firstFilterValue;
    }

    public void setFirstFilterValue(String firstFilterValue) {
        this.firstFilterValue = firstFilterValue;
    }

    public String getSecondFilterValue() {
        return secondFilterValue;
    }

    public void setSecondFilterValue(String secondFilterValue) {
        this.secondFilterValue = secondFilterValue;
    }
}
