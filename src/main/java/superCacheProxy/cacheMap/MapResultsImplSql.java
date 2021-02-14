package superCacheProxy.cacheMap;

import jdbc.dao.PortionDao;
import jdbc.dao.PortionDaoImpl;
import jdbc.model.Portion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapResultsImplSql implements MapResults {

    static private Map <Object, Integer> resultBySql = new ConcurrentHashMap <>();
    static private PortionDao portionDao = new PortionDaoImpl();

    @Override
    public Object put(Object key, Object value, Method invokeMethod) {

        Portion model = portionDao.createPortion(key, value);
        return resultBySql.put(key, model.getId());

    }

    @Override
    public Object get(Object key, Method invokeMethod) {

        Object result;
        Portion model = portionDao.findById(resultBySql.get(key));
        result = model.getValue();
        return result;

    }

    @Override
    public boolean containsKey(Object key, Method invokeMethod) {
        return resultBySql.containsKey(key);
    }

    @Override
    public void saveCache() throws SQLException, IOException, ClassNotFoundException {

    }

    @Override
    public void loadCache() throws SQLException, IOException, ClassNotFoundException {
        portionDao.loadCash(resultBySql);
    }
}
