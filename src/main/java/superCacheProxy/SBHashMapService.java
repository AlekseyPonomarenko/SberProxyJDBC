package superCacheProxy;
import superCacheProxy.cacheMap.MapResults;
import superCacheProxy.cacheMap.MapResultsImplFile;
import superCacheProxy.cacheMap.MapResultsImplInMemory;
import superCacheProxy.cacheMap.MapResultsImplSql;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class SBHashMapService {

    static private Map<Object, MapResults> map = new HashMap<>();
    public static void init(){
        map.put(Cache.СacheType.SQLITE, new MapResultsImplSql());
        map.put(Cache.СacheType.IN_MEMORY, new MapResultsImplInMemory());
        map.put(Cache.СacheType.FILE, new MapResultsImplFile());
    }

    static public Object put(Object key, Object value, Method invokeMethod) {
        System.out.println("Добавили в кэш:" + value);
        ParamsForCache paramsForCache = new ParamsForCache(invokeMethod);
        return map.get(paramsForCache.cacheType).put(key, value, invokeMethod);
    }

    static public Object get(Object key, Method invokeMethod) {

        ParamsForCache paramsForCache=new ParamsForCache(invokeMethod);
        Object result = map.get(paramsForCache.cacheType).get(key, invokeMethod);
        System.out.println("Получили из кэша " + result);
        return result;

    }

    static public boolean containsKey(Object key, Method invokeMethod) {
        ParamsForCache paramsForCache = new ParamsForCache(invokeMethod);
        return map.get(paramsForCache.cacheType).containsKey(key, invokeMethod);
    }


    static public void loadCache(Cache.СacheType cacheType) throws SQLException, IOException, ClassNotFoundException {
        map.get(cacheType).loadCache();
    }

    static public void saveCache(Cache.СacheType cacheType) throws SQLException, IOException, ClassNotFoundException {
        map.get(cacheType).saveCache();
    }

}
