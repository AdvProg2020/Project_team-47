package model.others;

public class ClientFilter {
    private final String name;
    private final String type;
    private final String unit;
    private final String[] fields;


    public ClientFilter(String name, String type, String unit, String... fields) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.fields = fields;
    }


}
