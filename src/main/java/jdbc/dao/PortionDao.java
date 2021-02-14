package jdbc.dao;

import jdbc.model.Portion;

public interface PortionDao {

    public Portion findById(int id);
    public int save(Portion portion);

}
