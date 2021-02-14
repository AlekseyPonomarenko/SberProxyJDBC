package primaryTasks;

import superCacheProxy.Cache;

public interface Service {
    @Cache(cacheType = Cache.СacheType.SQLITE, fileNamePrefix = "data")
    Object doHardWork (Object t1, Object t2);
}
