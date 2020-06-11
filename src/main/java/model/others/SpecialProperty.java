package model.others;

public class SpecialProperty {
    private String type;//numeric or text
    private String key;
    private String value;
    private double numericValue;
    private String unit;

    public SpecialProperty(String key, double numericValue, String unit) {
        this.type = "numeric";
        this.key = key;
        this.numericValue = numericValue;
        this.unit = unit;
    }

    public SpecialProperty(String key, String value) {
        this.type = "text";
        this.key = key;
        this.value = value;
    }

    public SpecialProperty(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecialProperty that = (SpecialProperty) o;

        return key.equalsIgnoreCase(that.key);
    }

    @Override
    public int hashCode() {
        return key.toLowerCase().hashCode();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isItValid() {
        if (type == null || !(type.equals("numeric") || type.equals("text"))) return false;
        if (key == null || key.isEmpty()) return false;
        return !type.equals("numeric") || (unit != null && !unit.isEmpty());
    }

    public void confirmProperty(SpecialProperty property) {
        if (property.type.equals("text") && this.type.equals("text")) {
            if (this.value == null)
                this.value = "";
        } else if (property.type.equals("numeric") && this.type.equals("numeric")) {
            this.unit = property.unit;
        } else {
            this.type = property.type;
            if (property.type.equals("text"))
                this.value = "";
            else if (property.type.equals("numeric"))
                this.unit = property.unit;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(double numericValue) {
        this.numericValue = numericValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
