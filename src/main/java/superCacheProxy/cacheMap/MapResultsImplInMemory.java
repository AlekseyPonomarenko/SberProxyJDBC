package superCacheProxy.cacheMap;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapResultsImplInMemory implements MapResults {
    static private Map <Object, Object> resultByArg = new ConcurrentHashMap <>();

    @Override
    public Object put(Object key, Object value, Method invokeMethod) {
        return resultByArg.put(key, value);
    }

    @Override
    public Object get(Object key, Method invokeMethod) {
        return resultByArg.get(key);
    }

    @Override
    public boolean containsKey(Object key, Method invokeMethod) {
        return resultByArg.containsKey(key);
    }

    @Override
    public void saveCache() throws SQLException, IOException, ClassNotFoundException {

    }

    @Override
    public void loadCache() throws SQLException, IOException, ClassNotFoundException {

    }
}
