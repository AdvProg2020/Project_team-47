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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (type != null)
            stringBuilder.append("Type: ").append(type).append("\n");

        if (filterKey != null)
            stringBuilder.append("Key: ").append(filterKey).append("\n");

        if (firstFilterValue != null) {
            stringBuilder.append("First filter value : ").append(firstFilterValue).append("\n");
            if (secondFilterValue != null)
                stringBuilder.append("Second filter value: ").append(secondFilterValue).append("\n");
        } else if (firstInt != 0) {
            stringBuilder.append("First filter value : ").append(firstInt).append("\n");
            if (secondInt != 0)
                stringBuilder.append("Second filter value: ").append(secondInt).append("\n");
        } else if (firstDouble != 0) {
            stringBuilder.append("First filter value : ").append(firstDouble).append("\n");
            if (secondDouble != 0)
                stringBuilder.append("Second filter value: ").append(secondDouble).append("\n");
        }

        return stringBuilder.toString();
    }

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
