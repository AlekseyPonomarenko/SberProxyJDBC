package superCacheProxy.cacheMap;

import superCacheProxy.Cache;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;

public interface MapResults {

    public Object put(Object key, Object value, Method invokeMethod);

    public Object get(Object key, Method invokeMethod);

    public boolean containsKey(Object key, Method invokeMethod);

    public void saveCache() throws SQLException, IOException, ClassNotFoundException;

    public void loadCache() throws SQLException, IOException, ClassNotFoundException;
}
