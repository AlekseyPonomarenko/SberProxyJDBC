package jdbc.dao;

import jdbc.model.Portion;

public interface PortionDao {

    public Portion findById(int id);
    public Portion createPortion(Object key, Object value);


}
