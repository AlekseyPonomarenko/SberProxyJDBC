package jdbc.model;

import java.io.Serializable;

public class Portion implements Serializable {

    private Integer id;
    private Object key;
    private Object value;

    public static Portion create(Object key, Object value){
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

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key=key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value=value;
    }

    @Override
    public String toString() {
        return "Portion{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
