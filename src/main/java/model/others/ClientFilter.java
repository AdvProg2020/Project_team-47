package model.others;

public class ClientFilter {
    private String name;
    private String type;
    private String unit;
    private String[] fields;


    public ClientFilter(String name, String type, String unit, String... fields) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.fields = fields;
    }


}
