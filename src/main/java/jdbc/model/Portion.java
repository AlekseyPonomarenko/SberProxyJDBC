package jdbc.model;

public class Portion {

    private Integer id;
    private String key;
    private String value;

    public static Portion create(String key, String value){
        Portion p = new Portion();
        p.setKey(key);
        p.setValue(value);
        return p;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key=key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value=value;
    }
}