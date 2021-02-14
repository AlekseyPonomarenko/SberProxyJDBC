package jdbc.dao;

import jdbc.model.Portion;

import java.sql.SQLException;
import java.util.Map;

public interface PortionDao {

    public Portion findById(int id);
    public Portion createPortion(Object key, Object value);
    public void loadCash(Map <Object, Integer> resultBySql) throws SQLException;


}
